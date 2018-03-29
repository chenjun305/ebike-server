package com.ecgobike.aop;

/**
 * Created by ChenJun on 2018/3/10.
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.util.JacksonUtil;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.web.servlet.HandlerInterceptor;


public abstract class BaseInterceptor implements HandlerInterceptor {

    /**
     * 返回错误消息
     *
     * @param response
     * @param errorCode
     * @param msg
     * @param data
     * @throws IOException
     */
    protected void sendErrorMsg(HttpServletResponse response, int errorCode, String msg, Object data) throws IOException {
        MessageDto dto = new MessageDto();
        dto.setCode(errorCode);
        dto.setMsg(msg);
        dto.setData(data);
        String result = JacksonUtil.toJson(dto);
        response.setHeader("Content-Type", "application/json; charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(result);
        writer.flush();
    }

    /**
     * 返回错误消息
     *
     * @param response
     * @param errorCode
     * @param msg
     * @throws IOException
     */
    protected void sendErrorMsg(HttpServletResponse response, int errorCode, String msg) throws IOException {
        sendErrorMsg(response, errorCode, msg, null);
    }

    /**
     * 返回错误消息
     *
     * @param response
     * @param errorCode
     * @throws IOException
     */
    protected void sendErrorMsgByCode(HttpServletResponse response, int errorCode) throws IOException {
        sendErrorMsg(response, errorCode, ErrorConstants.getDesc(errorCode), null);
    }

    /**
     * 返回错误消息
     *
     * @param response
     * @param errorCode
     * @param data
     * @throws IOException
     */
    protected void sendErrorMsgByCode(HttpServletResponse response, int errorCode, Object data) throws IOException {
        sendErrorMsg(response, errorCode, ErrorConstants.getDesc(errorCode), data);
    }

    protected boolean contains(String uri, List<String> container) {
        for (String exclude : container) {
            if (uri.endsWith(exclude) || uri.startsWith(exclude)) {
                return true;
            }
        }
        return false;
    }
}
