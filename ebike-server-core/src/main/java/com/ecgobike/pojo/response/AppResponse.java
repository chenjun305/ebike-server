package com.ecgobike.pojo.response;

import lombok.Data;
import com.ecgobike.common.constant.ErrorConstants;

@Data
public class AppResponse {

    private int code;

    private String msg;

    private Object data;

    public AppResponse() {
    }

    public AppResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static AppResponse responseSuccess() {
        return new AppResponse(ErrorConstants.SUCC, "succ!");
    }

    public static AppResponse responseSuccess(Object data) {
        AppResponse dto = new AppResponse(ErrorConstants.SUCC, "succ!");
        dto.setData(data);
        return dto;
    }

}
