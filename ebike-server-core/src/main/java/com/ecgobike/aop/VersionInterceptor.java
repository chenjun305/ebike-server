package com.ecgobike.aop;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.ErrorConstants;
import com.google.common.base.Strings;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/5/7.
 */
public class VersionInterceptor extends BaseInterceptor {
    List<String> excludes = Arrays.asList(new String[] {"/admin/"});

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (handler instanceof HandlerMethod) {
            String uri = request.getRequestURI();
            System.out.println("VersionInterceptor:" + uri);
            if (contains(uri, excludes)) {
                return true;
            }

            String version = request.getHeader("Version");
            if (Strings.isNullOrEmpty(version)) {
                sendErrorMsg(response, ErrorConstants.ERR_PARAMS, "lack Version info!");
                return false;
            }
            String value = Constants.SERVER_VERSION;
            if (!version.equals(value)) {//版本号与服务器不一致
                //TODO:目前简单强制更新
                Map<String, Object> data = new HashMap<>();
                data.put("android", "");
                data.put("ios", "");
                sendErrorMsgByCode(response, ErrorConstants.ERR_WRONG_VERSION, data);
                return false;
            }
        }
        return true;
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
