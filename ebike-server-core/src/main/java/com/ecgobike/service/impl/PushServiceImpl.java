package com.ecgobike.service.impl;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.AppType;
import com.ecgobike.common.enums.OsType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.UserToken;
import com.ecgobike.repository.UserTokenRepository;
import com.ecgobike.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/5/18.
 */
@Service
public class PushServiceImpl implements PushService {

    @Autowired
    UserTokenRepository userTokenRepository;

    @Override
    public UserToken refreshToken(String uid, AppType appType, String fcmToken, OsType osType, String apnsToken) throws GException {
        if (osType == OsType.IOS && (apnsToken == null || apnsToken.length() == 0)) {
            throw new GException(ErrorConstants.LACK_PARAM_APNS_TOKEN);
        }
        UserToken userToken = userTokenRepository.findFirstByUidAndAppTypeAndOsType(uid, appType, osType);
        if (userToken == null) {
            userToken = new UserToken();
            userToken.setUid(uid);
            userToken.setAppType(appType);
            userToken.setFcmToken(fcmToken);
            userToken.setOsType(osType);
            if (osType == OsType.IOS) {
                userToken.setApnsToken(apnsToken);
            }
            userToken.setStatus(1);
        }
        if (!userToken.getFcmToken().equals(fcmToken)) {
            userToken.setFcmToken(fcmToken);
            userToken.setUpdateTime(LocalDateTime.now());
        }
        if (osType == OsType.IOS && !userToken.getApnsToken().equals(apnsToken)) {
            userToken.setApnsToken(apnsToken);
            userToken.setUpdateTime(LocalDateTime.now());
        }
        userToken.setRefreshTime(LocalDateTime.now());
        return userTokenRepository.save(userToken);
    }
}
