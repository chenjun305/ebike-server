package com.ecgobike.service.sms;

import com.ecgobike.common.cache.StringCacheService;
import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.enums.SmsType;
import com.ecgobike.service.sms.operator.TencentSmsOperator;
import com.google.common.base.Strings;
import com.ecgobike.common.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsServiceImpl implements SmsService {

    @Autowired
    TencentSmsOperator smsOperator;

    @Override
    public boolean sendPin(String phoneNumber, SmsType type) {
        String key = cachePrefixBySmsType(type) + phoneNumber;

        String pin = StringCacheService.get(key);
        if (Strings.isNullOrEmpty(pin)) {
            pin = Utils.randomString(4, (byte)0B001);
            StringCacheService.set(key, pin, 600);
        }
        boolean result = smsOperator.send(phoneNumber, pin);
        return result;
    }

    @Override
    public boolean isPinValid(String phoneNumber, String pin, SmsType type) {
        String key = cachePrefixBySmsType(type) + phoneNumber;
        String cachedPin = StringCacheService.get(key);
        if (Strings.isNullOrEmpty(cachedPin) || !cachedPin.equals(pin)) {
            return false;
        }
        // delete after verify sms pin
        StringCacheService.del(key);
        return true;
    }

    private String cachePrefixBySmsType(SmsType type) {
        switch (type) {
            case USER_LOGIN:
                return Constants.cache_prefix_sms_user_login_pin;
            case STAFF_LOGIN:
                return Constants.cache_prefix_sms_staff_login_pin;
            case ADMIN_LOGIN:
                return Constants.cache_prefix_sms_admin_login_pin;
            case SELL_EBIKE:
                return Constants.cache_prefix_sms_sell_ebike_pin;
        }
        return "";
    }

}
