package com.ecgobike.pojo.response;

import lombok.Data;
import com.ecgobike.common.constant.ErrorConstants;

@Data
public class MessageDto {

    private int code;

    private String msg;

    private Object data;

    public MessageDto() {
    }

    public MessageDto(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static MessageDto responseSuccess() {
        return new MessageDto(ErrorConstants.SUCC, "succ!");
    }

    public static MessageDto responseSuccess(Object data) {
        MessageDto dto = new MessageDto(ErrorConstants.SUCC, "succ!");
        dto.setData(data);
        return dto;
    }

}
