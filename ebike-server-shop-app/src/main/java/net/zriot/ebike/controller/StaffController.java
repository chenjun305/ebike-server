package net.zriot.ebike.controller;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.util.AuthUtil;
import net.zriot.ebike.entity.staff.Staff;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.sms.SmsService;
import net.zriot.ebike.service.staff.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/19.
 */
@RestController
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    SmsService smsService;

    @Autowired
    StaffService staffService;

    @PostMapping("/pin")
    public MessageDto pin(String tel) throws GException {
        Staff staff = staffService.findOneByTel(tel);
        if (staff == null) {
            throw new GException(ErrorConstants.NOT_EXIST_STAFF);
        }
        boolean result = smsService.sendPin(tel);
        if (result) {
            return MessageDto.responseSuccess();
        } else {
            throw new GException(ErrorConstants.SEND_PIN_FAILED);
        }
    }

    @PostMapping("/login")
    public MessageDto login(String tel, String pin) throws GException {
        Staff staff = staffService.findOneByTel(tel);
        if (staff == null) {
            throw new GException(ErrorConstants.NOT_EXIST_STAFF);
        }

        if (! smsService.isPinValid(tel, pin)) {
            throw new GException(ErrorConstants.SMS_PIN_INVALID);
        }

        String uid = staff.getUid();
        String signMat = AuthUtil.buildSignMaterial(uid);
        String token = AuthUtil.buildToken(uid, signMat);

        Map<String, Object> data = new HashMap<>();
        data.put("staff", staff);

        data.put("uid", uid);
        data.put("signMat",signMat);
        data.put("token", token);
        return MessageDto.responseSuccess(data);
    }
}
