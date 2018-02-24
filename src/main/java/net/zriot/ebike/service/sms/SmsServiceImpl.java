package net.zriot.ebike.service.sms;

import net.zriot.ebike.service.sms.operator.TencentSmsOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    TencentSmsOperator smsOperator;

    @Override
    public boolean sendPin(String phoneNumber) {
        boolean result = smsOperator.send(phoneNumber, "1234");
        return result;
    }
}
