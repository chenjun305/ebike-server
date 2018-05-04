package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.PaymentOrderVO;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.StaffService;
import org.dozer.Mapper;
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

    @Autowired
    Mapper mapper;

    @RequestMapping("/today")
    @AuthRequire(Auth.STAFF)
    public AppResponse today(AuthParams params) {
        Long shopId = staffService.getShopIdByUid(params.getUid());
        List<Map> finance = paymentOrderService.sumDailyShopIncomeGroupByType(shopId, LocalDate.now());
        Map<String, Object> data = new HashMap<>();
        data.put("financeToday", finance);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/history")
    @AuthRequire(Auth.STAFF)
    public AppResponse history(AuthParams params,
                               @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Long shopId = staffService.getShopIdByUid(params.getUid());
        Page<PaymentOrder> history = paymentOrderService.findAllInShop(shopId, pageable);
        Page<PaymentOrderVO> historyVO = history.map(paymentOrder -> mapper.map(paymentOrder, PaymentOrderVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("history", historyVO);
        return AppResponse.responseSuccess(data);
    }
}
