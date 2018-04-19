package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.FileType;
import com.ecgobike.common.enums.Gender;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.request.UserUpdateParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.FileService;
import com.ecgobike.service.sms.SmsService;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.AuthUtil;
import com.ecgobike.entity.User;
import com.ecgobike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    SmsService smsService;

    @Autowired
    UserService userService;

    @Autowired
    FileService fileService;

    @PostMapping("/pin")
    public AppResponse pin(String tel) throws GException {
        boolean result = smsService.sendPin(tel);
        if (result) {
            return AppResponse.responseSuccess();
        } else {
            throw new GException(ErrorConstants.SEND_PIN_FAILED);
        }
    }

    @PostMapping("/login")
    public AppResponse login(String tel, String pin) throws GException {

        if (! smsService.isPinValid(tel, pin)) {
            throw new GException(ErrorConstants.SMS_PIN_INVALID);
        }
        User user = userService.getOrCreate(tel);

        String uid = user.getUid();
        String signMat = AuthUtil.buildSignMaterial(uid);
        String token = AuthUtil.buildToken(uid, signMat);

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        data.put("uid", uid);
        data.put("signMat",signMat);
        data.put("token", token);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/get")
    @AuthRequire(Auth.USER)
    public AppResponse get(AuthParams params) throws GException {
        String uid = params.getUid();
        User user = userService.getUserByUid(uid);

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/update")
    @AuthRequire(Auth.USER)
    public AppResponse update(@RequestParam(value = "avatar", required = false) MultipartFile file, UserUpdateParams params) throws GException {
        String uid = params.getUid();
        Byte gender = params.getGender();
        User user = userService.getUserByUid(uid);
        if (gender != null && Gender.getType(gender) == null) {
            throw new GException(ErrorConstants.ERR_PARAMS);
        }
        if (gender != null) {
            user.setGender(gender);
        }
        if (params.getAddress() != null) {
            user.setAddress(params.getAddress());
        }
        if (params.getNickname() != null) {
            user.setNickname(params.getNickname());
        }
        if (file != null && !file.isEmpty()) {
            String fileName = fileService.saveFile(FileType.USER_AVATAR, uid, file);
            user.setAvatar(fileName);
        }
        user = userService.update(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return AppResponse.responseSuccess(data);
    }
}
