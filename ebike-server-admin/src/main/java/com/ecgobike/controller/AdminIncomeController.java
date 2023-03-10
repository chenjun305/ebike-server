package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.ShopIncomeDaily;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.PaymentOrderVO;
import com.ecgobike.pojo.response.ShopIncomeDailyVO;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.ShopIncomeDailyService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/13.
 */
@RestController
@RequestMapping("/income")
public class AdminIncomeController {

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    ShopIncomeDailyService shopIncomeDailyService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<PaymentOrder> list = paymentOrderService.findAllShopIncome(pageable);
        Page<PaymentOrderVO> paymentOrderVOPage = list.map(paymentOrder -> mapper.map(paymentOrder, PaymentOrderVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("list", paymentOrderVOPage);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/daily/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse dailyList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ShopIncomeDaily> page = shopIncomeDailyService.findAll(pageable);
        Page<ShopIncomeDailyVO> voPage = page.map(shopIncomeDaily -> mapper.map(shopIncomeDaily, ShopIncomeDailyVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("list", voPage);
        return AppResponse.responseSuccess(data);
    }
}
