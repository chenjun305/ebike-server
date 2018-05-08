package com.ecgobike.service.sms;

import com.ecgobike.common.enums.SmsType;

public interface SmsService {
    boolean sendPin(String phoneNumber, SmsType type);

    boolean isPinValid(String phoneNumber, String pin, SmsType type);
}
