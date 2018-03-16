package net.zriot.ebike.aop;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.constant.Constants;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.common.util.AES256;
import net.zriot.ebike.common.util.DateUtils;
import net.zriot.ebike.common.util.Utils;
import net.zriot.ebike.helper.UserHelper;
import net.zriot.ebike.entity.user.User;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class AuthInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod method = ((HandlerMethod) handler);
            AuthRequire classAuthRequire = method.getBeanType().getAnnotation(AuthRequire.class);
            AuthRequire methodAuthRequire = method.getMethodAnnotation(AuthRequire.class);
            if (methodAuthRequire == null && classAuthRequire == null) {//类和方法上都为空，返回默认配置
                return defaultAuth;
            }else if (methodAuthRequire == null && classAuthRequire != null) {//方法上没有指定，按照类上面的指定进行
                //类上面有标记
                Auth auth = classAuthRequire.value();
                return chargeAuth(auth, request, response);
            }else if(methodAuthRequire != null) {//优先对方法进行
                Auth auth = methodAuthRequire.value();
                return chargeAuth(auth, request, response);
            }
        }
        return defaultAuth;
    }

    protected boolean chargeAuth(Auth auth, HttpServletRequest request, HttpServletResponse response) throws IOException {
        //碰到有null的直接检测通过
        if (auth == Auth.NULL) {
            return true;
        }

        if (auth == Auth.LOGIN) {
            Map<String, String[]> map = request.getParameterMap();
            String[] uidCells = map.get("uid");
            String[] tokenCells = map.get("token");
            String[] signCells = map.get("sign");
            if (!Utils.notNull(uidCells) || !Utils.notNull(tokenCells) || !Utils.notNull(signCells)) {
                sendErrorMsgByCode(response, ErrorConstants.LACK_PARAMS_CHECK_SIGN);
                return false;
            }

            String token = tokenCells[0];
            if (Strings.isNullOrEmpty(token)) {
                sendErrorMsgByCode(response, ErrorConstants.LACK_PARAMS_TOKEN);
                return false;
            }

            try {
                token = AES256.decrypt(token, Constants.TOKEN_SALT);
            } catch (Exception e1) {
                sendErrorMsgByCode(response, ErrorConstants.ILLEGAL_TOKEN);
                return false;
            }
            String[] tCells = token.split("[|]");
            if (tCells == null || tCells.length != 3) {
                sendErrorMsgByCode(response, ErrorConstants.ILLEGAL_TOKEN);
                return false;
            }

            String tokenUid = tCells[0];
            String time = tCells[1];
            try{
                Integer loginTime = Integer.parseInt(time);
                if (loginTime + Constants.DAY30_2_SECOND < DateUtils.getNow()) {// 登录时间已经过期
                    sendErrorMsgByCode(response, ErrorConstants.LOGIN_OVERDUE);
                    return false;
                }
            }catch (Exception e) {
                sendErrorMsgByCode(response, ErrorConstants.ILLEGAL_PARAMS_CHECK_SIGN);
                return false;
            }

            String tokenSignMat = tCells[2];
            String uid = uidCells[0];
            if (!tokenUid.equals(uid)) {// uid检测非法
                sendErrorMsgByCode(response, ErrorConstants.ILLEGAL_UID);
                return false;
            }


            TreeMap<String, String> checkMap = new TreeMap<>();
            for (Map.Entry<String, String[]> entry : map.entrySet()) {
                String key = entry.getKey();
                if ("sign".equals(key) || "token".equals(key)) {
                    continue;
                }
                String value = entry.getValue()[0];
                checkMap.put(key, value);
            }

            StringBuffer orginSource = new StringBuffer();
            for (Map.Entry<String, String> entry : checkMap.entrySet()) {
                String value = entry.getValue();
                orginSource.append(value);
            }
            orginSource.append(tokenSignMat);

            String sign = signCells[0];
            String check = Utils.getMD5(orginSource.toString());
            System.out.println("check="+check);
            System.out.println("sign="+sign);
            if (!check.equals(sign)) {
                sendErrorMsgByCode(response, ErrorConstants.ERR_SIGN);
                return false;
            }

            // 检测用户是否存在
            User user = UserHelper.findUserByUid(uid);
            if (user == null) {
                sendErrorMsgByCode(response, ErrorConstants.USER_NOT_FOUND);
                return false;
            }
            return true;
        }
        return defaultAuth;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        // TODO Auto-generated method stub

    }

    /**
     * 默认是否进行验证权限
     */
    private boolean defaultAuth = true;

    public boolean isDefaultAuth() {
        return defaultAuth;
    }

    public void setDefaultAuth(boolean defaultAuth) {
        this.defaultAuth = defaultAuth;
    }

}

