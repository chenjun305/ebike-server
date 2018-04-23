package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.AuthUtil;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.ShopStaff;
import com.ecgobike.entity.User;
import com.ecgobike.entity.UserRole;
import com.ecgobike.pojo.request.StaffParams;
import com.ecgobike.service.ShopService;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.ShopStaffService;
import com.ecgobike.service.UserRoleService;
import com.ecgobike.service.UserService;
import com.ecgobike.service.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jws.soap.SOAPBinding;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/21.
 */
@RestController
@RequestMapping("/staff")
public class AdminStaffController {

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    UserService userService;

    @Autowired
    SmsService smsService;

    @Autowired
    ShopService shopService;

    @Autowired
    ShopStaffService shopStaffService;

    @PostMapping("/pin")
    public AppResponse pin(String tel) throws GException {
        User user = userService.getUserByTel(tel);
        if (user == null) {
            throw new GException(ErrorConstants.USER_NOT_FOUND);
        }
        UserRole userRole = userRoleService.findOneByUidAndRole(user.getUid(), StaffRole.OPERATE);
        if (userRole == null) {
            throw new GException(ErrorConstants.NOT_ADMIN);
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
        User user = userService.getUserByTel(tel);
        if (user == null) {
            throw new GException(ErrorConstants.USER_NOT_FOUND);
        }
        String uid = user.getUid();
        UserRole userRole = userRoleService.findOneByUidAndRole(uid, StaffRole.OPERATE);
        if (userRole == null) {
            throw new GException(ErrorConstants.NOT_ADMIN);
        }

        if (! smsService.isPinValid(tel, pin)) {
            throw new GException(ErrorConstants.SMS_PIN_INVALID);
        }

        String signMat = AuthUtil.buildSignMaterial(uid);
        String token = AuthUtil.buildToken(uid, signMat);

        Map<String, Object> data = new HashMap<>();
        data.put("staff", user);
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
        Page<ShopStaff> staffs = shopStaffService.findAll(pageable);
        data.put("staffs", staffs);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/create")
    @AuthRequire(Auth.ADMIN)
    public AppResponse create(StaffParams params) throws GException {
        Long shopId = params.getShopId();
        if (shopId != null && shopId > 0) {
            Shop shop = shopService.getShopById(params.getShopId());
            if (shop == null) {
                throw new GException(ErrorConstants.NOT_EXIST_SHOP);
            }
        } else {
            if (params.getRole() == StaffRole.SHOP_OWNER.get() || params.getRole() == StaffRole.SHOP_STAFF.get()) {
                throw new GException(ErrorConstants.NOT_EXIST_SHOP);
            }
        }

        User user = userService.getOrCreate(params.getTel());
        String uid = user.getUid();
        UserRole userRole = userRoleService.findOneByUidAndRole(uid, StaffRole.getRole(params.getRole()));
        if (userRole != null) {
            throw new GException(ErrorConstants.ALREADY_EXIST_STAFF);
        }

        user.setIsReal((byte)1);
        user.setGender(params.getGender());
        user.setRealName(params.getRealName());
        user.setIdCardNum(params.getIdCardNum());
        user.setAddress(params.getAddress());
        user.setUpdateTime(LocalDateTime.now());
        userService.update(user);

        userRoleService.create(uid, StaffRole.getRole(params.getRole()));
        if (params.getRole() == StaffRole.SHOP_STAFF.get() || params.getRole() == StaffRole.SHOP_OWNER.get()) {
            shopStaffService.create(uid, params.getShopId(), params.getStaffNum());
        }

        Map<String, Object> data = new HashMap<>();
        data.put("staff", user);
        return AppResponse.responseSuccess(data);
    }
}
