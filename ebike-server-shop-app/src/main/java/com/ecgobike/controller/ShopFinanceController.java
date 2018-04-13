package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.Staff;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/13.
 */
@RestController
@RequestMapping("/finance")
public class ShopFinanceController {

    @Autowired
    StaffService staffService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @RequestMapping("/today")
    @AuthRequire(Auth.STAFF)
    public AppResponse today(AuthParams params) {
        Staff staff = staffService.findOneByUid(params.getUid());
        List<Map<OrderType, BigDecimal>> finance = paymentOrderService.sumDailyShopIncomeGroupByType(staff.getShopId(), LocalDate.now());
        Map<String, Object> data = new HashMap<>();
        data.put("financeToday", finance);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/history")
    @AuthRequire(Auth.STAFF)
    public AppResponse history(AuthParams params,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Staff staff = staffService.findOneByUid(params.getUid());
        Page<PaymentOrder> history = paymentOrderService.findAllInShop(staff.getShopId(), pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("history", history);
        return AppResponse.responseSuccess(data);
    }
}
