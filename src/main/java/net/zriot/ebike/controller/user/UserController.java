package net.zriot.ebike.controller.user;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.common.enums.Gender;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.util.AuthUtil;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.request.user.UserUpdateParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.model.user.User;
import net.zriot.ebike.service.sms.SmsService;
import net.zriot.ebike.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    SmsService smsService;

    @Autowired
    UserService userService;

    @PostMapping("/pin")
    public MessageDto pin(String tel) {
        boolean result = smsService.sendPin(tel);
        if (result) {
            return MessageDto.responseSuccess();
        } else {
            MessageDto message = new MessageDto(ErrorConstants.FAIL, "send failed");
            return message;
        }
    }

    @PostMapping("/login")
    public MessageDto login(String tel, String pin) {
        System.out.println(tel+pin);
        if (! smsService.isPinValid(tel, pin)) {
            return new MessageDto(ErrorConstants.SMS_PIN_INVALID, "sms pin invalid");

        }
        User user = userService.login(tel);

        String uid = user.getUid();
        String signMat = AuthUtil.buildSignMaterial(uid);
        String token = AuthUtil.buildToken(uid, signMat);

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        data.put("uid", uid);
        data.put("signMat",signMat);
        data.put("token", token);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/get")
    @AuthRequire(Auth.LOGIN)
    public MessageDto get(AuthParams params) throws GException {
        String uid = params.getUid();
        User user = userService.getUserByUid(uid);

        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/update")
    @AuthRequire(Auth.LOGIN)
    public MessageDto update(UserUpdateParams params, AuthParams authParams) throws GException {
        Byte gender = params.getGender();
        if (gender != null && Gender.getType(gender) == null) {
            throw new GException(ErrorConstants.ERR_PARAMS);
        }
        User user = userService.updateByUid(authParams.getUid(), params);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return MessageDto.responseSuccess(data);
    }
}
