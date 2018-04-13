package com.ecgobike.common.exception;

import com.google.common.base.Strings;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.log.LogHelp;
import com.ecgobike.pojo.response.AppResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by ChenJun on 2018/3/12.
 * 全局异常处理
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler(GException.class)
    @ResponseBody
    AppResponse handGException(HttpServletRequest request, GException e) {
        AppResponse result = new AppResponse();
        result.setCode(e.getErr());
        if (Strings.isNullOrEmpty(e.getMsg())) {
            result.setMsg(ErrorConstants.getDesc(e.getErr()));
        }else {
            result.setMsg(e.getMsg());
        }
        LogHelp.doLogAppErr(e);
        log.error(e.getMessage(), e);
        return result;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    AppResponse handException(HttpServletRequest request, Exception e) {
        AppResponse result = new AppResponse();
        result.setCode(ErrorConstants.FAIL);
        result.setMsg(e.getMessage());
        LogHelp.doLogException(e);
        log.error(e.getMessage());
        return result;
    }
}
