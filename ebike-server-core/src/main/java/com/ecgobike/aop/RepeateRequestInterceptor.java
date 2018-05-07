package com.ecgobike.aop;

import com.ecgobike.common.cache.StringCacheService;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.util.Utils;
import com.google.common.base.Strings;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * Created by ChenJun on 2018/5/7.
 */
public class RepeateRequestInterceptor extends BaseInterceptor {

    private static final String db_prefix = "repeate_forbidden_";

    private static List<String> oneMinuteCheckUri = Arrays.asList(new String[]{"/user/pin", "/staff/pin"});

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();
        String remoteAddr = request.getRemoteAddr();
        System.out.println("RepeateRequestInterceptor:" + uri + ", remoteAddr:" + remoteAddr);
        String md5 = Utils.getMD5(uri + remoteAddr);
        String check = StringCacheService.get(db_prefix + md5);
        if (Strings.isNullOrEmpty(check)) {//可以正常访问
            int checkTime = 3; // 默认3秒中防御检测
            if (contains(uri, oneMinuteCheckUri)) {// 1分钟防御检测
                checkTime = 60;
            }
            StringCacheService.set(db_prefix + md5, "S", checkTime);
            return true;
        }
        // 两次访问间隔太短
        sendErrorMsgByCode(response, ErrorConstants.ERR_REQUEST_TOO_FAST);
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {

    }
}
