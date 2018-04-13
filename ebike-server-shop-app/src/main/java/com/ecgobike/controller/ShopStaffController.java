package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.pojo.request.StaffParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.StaffService;
import com.ecgobike.service.sms.SmsService;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.util.AuthUtil;
import com.ecgobike.entity.Staff;
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
public class ShopStaffController {
    @Autowired
    SmsService smsService;

    @Autowired
    StaffService staffService;

    @PostMapping("/pin")
    public AppResponse pin(String tel) throws GException {
        Staff staff = staffService.findOneByTel(tel);
        if (staff == null) {
            throw new GException(ErrorConstants.NOT_EXIST_STAFF);
        }
        boolean result = smsService.sendPin(tel);
        if (result) {
            return AppResponse.responseSuccess();
        } else {
            throw new GException(ErrorConstants.SEND_PIN_FAILED);
        }
    }

    @PostMapping("/login")
    public AppResponse login(String tel, String pin) throws GException {
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
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/create")
    @AuthRequire(Auth.STAFF)
    public AppResponse create(StaffParams params) throws GException {
        Staff staff = staffService.findOneByTel(params.getTel());
        if (staff != null) {
            throw new GException(ErrorConstants.ALREADY_EXIST_STAFF);
        }
        staff = new Staff();
        staff.setUid(IdGen.uuid());
        staff.setTel(params.getTel());
        staff.setRealName(params.getRealName());
        staff.setGender(params.getGender());
        staff.setIdCardNum(params.getIdCardNum());
        staff.setShopId(params.getShopId());
        staff.setRole(params.getRole());
        staff.setStaffNum(params.getStaffNum());
        staff.setAddress(params.getAddress());
        staff.setStatus((byte)1);
        Staff newStaff = staffService.create(staff);
        Map<String, Object> data = new HashMap<>();
        data.put("staff", newStaff);
        return AppResponse.responseSuccess(data);
    }
}
