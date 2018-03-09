package net.zriot.ebike.service.sms;

import net.zriot.ebike.common.util.Utils;
import net.zriot.ebike.service.sms.operator.TencentSmsOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    TencentSmsOperator smsOperator;

    @Override
    public boolean sendPin(String phoneNumber) {
        String pin = Utils.randomString(4, (byte)0B001);
        boolean result = smsOperator.send(phoneNumber, pin);
        return result;
    }
}
