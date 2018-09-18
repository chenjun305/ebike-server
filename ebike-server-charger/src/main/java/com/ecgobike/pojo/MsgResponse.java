package com.ecgobike.pojo;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.pojo.response.AppResponse;
import lombok.Data;

/**
 * Created by ChenJun on 2018/9/18.
 */
@Data
public class MsgResponse {
    private int msgcode;

    private String msg;

    public MsgResponse() {
    }

    public MsgResponse(int msgcode, String msg) {
        this.msgcode = msgcode;
        this.msg = msg;
    }

    public static MsgResponse responseSuccess() {
        return new MsgResponse(0, "succ!");
    }
}
