package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.AuthUtil;
import com.ecgobike.service.StaffService;
import com.ecgobike.entity.Staff;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/21.
 */
@RestController
@RequestMapping("/staff")
public class AdminStaffController {

    @Autowired
    StaffService staffService;

    @Autowired
    SmsService smsService;

    @PostMapping("/pin")
    public AppResponse pin(String tel) throws GException {
        Staff staff = staffService.findOneByTel(tel);
        if (staff == null || staff.getRole() != StaffRole.OPERATE) {
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
        if (staff == null || staff.getRole() != StaffRole.OPERATE) {
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

    @RequestMapping("/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Map<String, Object> data = new HashMap<>();
        Page<Staff> staffs = staffService.findAll(pageable);
        data.put("staffs", staffs);
        return AppResponse.responseSuccess(data);
    }
}
