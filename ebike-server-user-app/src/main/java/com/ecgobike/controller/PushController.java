package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.AppType;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.OsType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.pojo.request.PushTokenParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.PushService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenJun on 2018/5/18.
 */
@RestController
@RequestMapping("/push")
public class PushController {
    @Autowired
    PushService pushService;

    @RequestMapping("/token")
    @AuthRequire(Auth.STAFF)
    public AppResponse token(PushTokenParams params) throws GException {
        OsType osType = OsType.getType(params.getOsType());
        pushService.refreshToken(params.getUid(), AppType.USER_APP, params.getFcmToken(), osType, params.getApnsToken());
        return AppResponse.responseSuccess();
    }
}
