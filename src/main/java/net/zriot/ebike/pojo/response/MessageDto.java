package net.zriot.ebike.pojo.response;

import net.zriot.ebike.common.constant.ErrorConstants;

public class MessageDto {

    private int code;

    private String msg;

    private Object data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

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

    @Override
    public String toString() {
        return "MessageDto{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
