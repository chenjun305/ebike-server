package com.ecgobike.service;

import com.ecgobike.common.enums.AppType;
import com.ecgobike.common.enums.OsType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.UserToken;

/**
 * Created by ChenJun on 2018/5/18.
 */
public interface PushService {
    UserToken refreshToken(String uid, AppType appType, String fcmToken, OsType osType, String apnsToken) throws GException;
}
