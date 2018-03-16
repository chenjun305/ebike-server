package net.zriot.ebike.service.sms;

public interface SmsService {
    boolean sendPin(String phoneNumber);

    boolean isPinValid(String phoneNumber, String pin);
}
