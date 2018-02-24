package net.zriot.ebike.controller.user;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.model.user.User;
import net.zriot.ebike.service.sms.SmsService;
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

    @RequestMapping("/login")
    public MessageDto login() {
        User user = new User();
        user.setId(1);
        user.setUid("aaabbbccc");
        user.setTel("13829780305");
        user.setIsReal((byte)0);
        user.setMoney(12345);
        user.setStatus((byte)1);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        data.put("uid", "xxxxxx");
        data.put("signMat", "sisnss");
        data.put("token", "tollll");
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/get")
    public MessageDto get() {
        User user = new User();
        user.setId(1);
        user.setUid("aaabbbccc");
        user.setTel("13829780305");
        user.setIsReal((byte)0);
        user.setMoney(12345);
        user.setStatus((byte)1);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return MessageDto.responseSuccess(data);
    }
}
