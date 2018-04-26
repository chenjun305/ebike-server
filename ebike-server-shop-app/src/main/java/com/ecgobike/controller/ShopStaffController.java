package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.ShopStaff;
import com.ecgobike.entity.User;
import com.ecgobike.entity.UserRole;
import com.ecgobike.pojo.request.StaffParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.StaffInfoVO;
import com.ecgobike.service.ShopService;
import com.ecgobike.service.ShopStaffService;
import com.ecgobike.service.UserRoleService;
import com.ecgobike.service.UserService;
import com.ecgobike.service.sms.SmsService;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.util.AuthUtil;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

/**
 * Created by ChenJun on 2018/3/19.
 */
@RestController
@RequestMapping("/staff")
public class ShopStaffController {
    @Autowired
    SmsService smsService;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    ShopStaffService shopStaffService;

//    @Autowired
//    ShopService shopService;
    @Autowired
    Mapper mapper;

    @PostMapping("/pin")
    public AppResponse pin(String tel) throws GException {
        User user = userService.getUserByTel(tel);
        if (user == null) {
            throw new GException(ErrorConstants.USER_NOT_FOUND);
        }
        ShopStaff shopStaff = shopStaffService.findOneByUid(user.getUid());
        if (shopStaff == null) {
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
        User user = userService.getUserByTel(tel);
        if (user == null) {
            throw new GException(ErrorConstants.USER_NOT_FOUND);
        }
        String uid = user.getUid();
        ShopStaff shopStaff = shopStaffService.findOneByUid(uid);
        if (shopStaff == null) {
            throw new GException(ErrorConstants.NOT_EXIST_STAFF);
        }

        List<UserRole> roleList = userRoleService.findAllByUid(uid);
        if (roleList.size() == 0) {
            throw new GException(ErrorConstants.AUTHENTICATION_FAIL);
        }


        if (! smsService.isPinValid(tel, pin)) {
            throw new GException(ErrorConstants.SMS_PIN_INVALID);
        }


        String signMat = AuthUtil.buildSignMaterial(uid);
        String token = AuthUtil.buildToken(uid, signMat);
        StaffInfoVO staffInfoVO = mapper.map(user, StaffInfoVO.class);
        staffInfoVO.setStaffNum(shopStaff.getStaffNum());
        staffInfoVO.setShop(shopStaff.getShop());

        List<StaffRole> roles = roleList.stream().map(UserRole::getRole).collect(toList());
        staffInfoVO.setRoles(roles);

        Map<String, Object> data = new HashMap<>();

        data.put("staff", staffInfoVO);

        data.put("uid", uid);
        data.put("signMat",signMat);
        data.put("token", token);
        return AppResponse.responseSuccess(data);
    }

//    @PostMapping("/create")
//    @AuthRequire(Auth.STAFF)
//    public AppResponse create(StaffParams params) throws GException {
//        UserRole shopOwner = userRoleService.findOneByUidAndRole(params.getUid(), StaffRole.SHOP_OWNER);
//        if (shopOwner == null) {
//            throw new GException(ErrorConstants.NOT_SHOP_OWNER);
//        }
//        User user = userService.getOrCreate(params.getTel());
//        ShopStaff shopStaff = shopStaffService.findOneByUid(user.getUid());
//        if (shopStaff != null) {
//            throw new GException(ErrorConstants.ALREADY_EXIST_STAFF);
//        }
//        user.setIsReal((byte)1);
//        user.setRealName(params.getRealName());
//        user.setGender(params.getGender());
//        user.setIdCardNum(params.getIdCardNum());
//        user.setAddress(params.getAddress());
//        userService.update(user);
//
//        Shop shop = shopStaffService.findOneByUid(params.getUid()).getShop();
//        shopStaffService.create(user.getUid(), shop, params.getStaffNum());
//        userRoleService.create(user.getUid(), StaffRole.getRole(params.getRole()));
//
//        Map<String, Object> data = new HashMap<>();
//        data.put("staff", user);
//        return AppResponse.responseSuccess(data);
//    }
}
