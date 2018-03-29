package com.ecgobike.service.sms.operator;

public interface SmsOperator {
    boolean send(String phoneNumber, String content);
}
