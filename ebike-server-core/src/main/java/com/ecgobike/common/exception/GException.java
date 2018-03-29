package com.ecgobike.common.exception;

import com.ecgobike.common.constant.ErrorConstants;

import java.io.Serializable;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class GException extends Exception implements Serializable {
    private static final long serialVersionUID = 1L;
    private int err = ErrorConstants.SUCC;
    private String msg;
    public int getErr() {
        return err;
    }
    public String getMsg(){
        return msg;
    }
    public GException(int err) {
        this.err = err;
//		this.msg = ErrorConstants.getDesc(err);
    }
    public GException(Exception e) {
        super(e);
        this.err = ErrorConstants.FAIL;
        this.msg = e.getMessage();
    }

    public GException(int err, Exception e) {
        super(ErrorConstants.getDesc(err), e);
        this.err = err;
        this.msg = ErrorConstants.getDesc(err);
    }

    public GException(int err, String msg, Exception e) {
        super(msg, e);
        this.err = err;
        this.msg = msg;
    }
}

