package com.ecgobike.aop;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.common.util.AES256;
import com.ecgobike.common.util.DateUtils;
import com.ecgobike.common.util.Utils;
import com.ecgobike.entity.Staff;
import com.ecgobike.entity.UserRole;
import com.ecgobike.helper.ShopStaffHelper;
import com.ecgobike.helper.UserHelper;
import com.ecgobike.entity.User;
import com.ecgobike.helper.UserRoleHelper;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class AuthInterceptor extends BaseInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        Map<String, String[]> map = request.getParameterMap();
//        System.out.println("=======request parameters=========");
//        for (Map.Entry<String, String[]> entry : map.entrySet()) {
//            String key = entry.getKey();
//            String value = entry.getValue()[0];
//            System.out.println(key + ":" + value);
//        }
//        System.out.println("=======end=========");
        // 读取请求内容
//        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(),"utf-8"));
//        String line = null;
//        StringBuilder sb = new StringBuilder();
//        while ((line = br.readLine()) != null) {
//            sb.append(line);
//        }
//        System.out.println("request:" + sb.toString());

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
            if (loginTime + Constants.DAY7_2_SECOND < DateUtils.getNow()) {// 登录时间已经过期
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
        System.out.println("=======request parameters=========");
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue()[0];
            System.out.println(key + ":" + value);
            if ("sign".equals(key) || "token".equals(key)) {
                continue;
            }
            checkMap.put(key, value);
        }
        System.out.println("=======end=========");

        StringBuffer orginSource = new StringBuffer();
        for (Map.Entry<String, String> entry : checkMap.entrySet()) {
            String value = entry.getValue();
            orginSource.append(value);
        }
        orginSource.append(tokenSignMat);

        String sign = signCells[0];
        String check = Utils.getMD5(orginSource.toString());
        if (!check.equals(sign)) {
            System.out.println("right sign="+check);
            sendErrorMsgByCode(response, ErrorConstants.ERR_SIGN);
            return false;
        }

        // 检测用户是否存在
        User user = UserHelper.findUserByUid(uid);
        if (user == null) {
            sendErrorMsgByCode(response, ErrorConstants.USER_NOT_FOUND);
            return false;
        }
        if (auth == Auth.STAFF) {
            Staff staff = ShopStaffHelper.findByUid(uid);
            if (staff == null) {
                sendErrorMsgByCode(response, ErrorConstants.NOT_EXIST_STAFF);
                return false;
            }
        } else if (auth == Auth.ADMIN) {
            UserRole admin = UserRoleHelper.findByUidAndRole(uid, StaffRole.OPERATE);
            if (admin == null) {
                sendErrorMsgByCode(response, ErrorConstants.NOT_ADMIN);
                return false;
            }
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

