package net.zriot.ebike.service.sms;

import com.google.common.base.Strings;
import net.zriot.ebike.common.cache.StringCacheService;
import net.zriot.ebike.common.constant.Constants;
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
        String pin = StringCacheService.get(Constants.cache_prefix_sms_pin + phoneNumber);
        if (Strings.isNullOrEmpty(pin)) {
            pin = Utils.randomString(4, (byte)0B001);
            StringCacheService.set(Constants.cache_prefix_sms_pin + phoneNumber, pin, 600);
        }
        boolean result = smsOperator.send(phoneNumber, pin);
        return result;
    }

    @Override
    public boolean isPinValid(String phoneNumber, String pin) {
        String cachedPin = StringCacheService.get(Constants.cache_prefix_sms_pin + phoneNumber);
        if (Strings.isNullOrEmpty(cachedPin) || !cachedPin.equals(pin)) {
            return false;
        }
        // delete after verify sms pin
        StringCacheService.del(Constants.cache_prefix_sms_pin + phoneNumber);
        return true;
    }


}
