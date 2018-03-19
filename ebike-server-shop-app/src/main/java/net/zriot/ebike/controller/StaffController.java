package net.zriot.ebike.controller;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ChenJun on 2018/3/19.
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
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
}
