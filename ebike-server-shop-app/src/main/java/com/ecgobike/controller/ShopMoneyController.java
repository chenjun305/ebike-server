package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.Staff;
import com.ecgobike.pojo.request.Money;
import com.ecgobike.pojo.request.TopupParams;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.StaffService;
import com.ecgobike.service.UserService;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/27.
 */
@RestController
@RequestMapping("/money")
public class ShopMoneyController {

    @Autowired
    UserService userService;

    @Autowired
    StaffService staffService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @PostMapping("/topup")
    @AuthRequire(Auth.STAFF)
    public MessageDto topup(TopupParams params) throws GException {
        User user = userService.getUserByTel(params.getTel());
        if (user == null) {
            throw new GException(ErrorConstants.USER_NOT_FOUND);
        }

        Staff staff = staffService.findOneByUid(params.getUid());

        String currency = params.getCurrency() == null ? "USD" : params.getCurrency();
        Money money = new Money(params.getAmount(), currency);
        user = userService.addMoney(user, money);

        PaymentOrder paymentOrder = paymentOrderService.createTopupOrder(staff, user, money);
        Map<String, Object> data = new HashMap<>();
        data.put("paymentOrder", paymentOrder);
        return MessageDto.responseSuccess(data);
    }
}
