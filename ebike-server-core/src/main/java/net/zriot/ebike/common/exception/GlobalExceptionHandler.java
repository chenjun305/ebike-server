package net.zriot.ebike.common.exception;

import com.google.common.base.Strings;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.log.LogHelp;
import net.zriot.ebike.pojo.response.MessageDto;
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
    MessageDto handGException(HttpServletRequest request, GException e) {
        MessageDto result = new MessageDto();
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
    MessageDto handException(HttpServletRequest request, Exception e) {
        MessageDto result = new MessageDto();
        result.setCode(ErrorConstants.FAIL);
        result.setMsg(e.getMessage());
        LogHelp.doLogException(e);
        log.error(e.getMessage());
        return result;
    }
}
