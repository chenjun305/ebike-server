package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.pojo.request.JoinParams;
import com.ecgobike.pojo.request.RenewParams;
import com.ecgobike.pojo.request.SellBikeParams;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.EBikeService;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.StaffService;
import com.ecgobike.service.UserService;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.Staff;
import com.ecgobike.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/26.
 */
@RestController
@RequestMapping("/ebike")
public class ShopEBikeController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @RequestMapping("/info")
    @AuthRequire(Auth.STAFF)
    public MessageDto info(String ebikeSn) throws GException {
        EBike eBike = eBikeService.findOneBySn(ebikeSn);
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/sell")
    @AuthRequire(Auth.STAFF)
    public MessageDto sell(SellBikeParams params) throws GException {
        EBike eBike = eBikeService.findOneBySn(params.getEbikeSn());
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (eBike.getUid() != null) {
            throw  new GException(ErrorConstants.ALREADY_SELLED);
        }
        Staff staff = staffService.findOneByUid(params.getUid());
        if (staff == null) {
            throw new GException(ErrorConstants.NOT_EXIST_STAFF);
        }
        User user = userService.getUserByTel(params.getPhoneNum());
        if (user == null) {
            // new user
            user = new User();
            user.setUid(IdGen.uuid());
            user.setTel(params.getPhoneNum());
        }
        user.setIsReal((byte)1);
        user.setRealName(params.getRealName());
        user.setAddress(params.getAddress());
        user = userService.update(user);

        eBike = eBikeService.sell(user, eBike);
        PaymentOrder paymentOrder = paymentOrderService.createSellOrder(staff, user, eBike);

        Map<String, Object> data = new HashMap<>();
        data.put("paymentOrder", paymentOrder);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/join")
    @AuthRequire(Auth.STAFF)
    public MessageDto join(JoinParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.joinMembership(params.getEbikeSn());
        PaymentOrder paymentOrder = paymentOrderService.createMembershipOrder(OrderType.STAFF_JOIN_MEMBERSHIP, eBike, staff);

        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        data.put("paymentOrder", paymentOrder);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/renew")
    @AuthRequire(Auth.STAFF)
    public MessageDto renew(RenewParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        EBike eBike = eBikeService.renew(params.getEbikeSn());
        PaymentOrder paymentOrder = paymentOrderService.createMembershipOrder(OrderType.STAFF_RENEW_MONTHLY, eBike, staff);

        Map<String, Object> data = new HashMap<>();
        data.put("ebike", eBike);
        data.put("paymentOrder", paymentOrder);
        return MessageDto.responseSuccess(data);
    }
}
