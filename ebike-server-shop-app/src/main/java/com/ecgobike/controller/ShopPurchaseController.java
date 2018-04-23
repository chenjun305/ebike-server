package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.ProductType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Logistics;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.entity.ShopStaff;
import com.ecgobike.pojo.request.AuthParams;
import com.ecgobike.pojo.request.PurchaseTakeParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/11.
 */
@RestController
@RequestMapping("/purchase")
public class ShopPurchaseController {
    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ShopStaffService shopStaffService;

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    ProductService productService;

    @Autowired
    BatteryService batteryService;

    @RequestMapping("/list")
    @AuthRequire(Auth.STAFF)
    public AppResponse list(
            AuthParams params,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable
    ){
        Long shopId = shopStaffService.findOneByUid(params.getUid()).getShopId();
        Page<PurchaseOrder> list = purchaseOrderService.findAllByShopId(shopId, pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/take")
    @AuthRequire(Auth.STAFF)
    public AppResponse take(PurchaseTakeParams params) throws GException {
        ShopStaff staff = shopStaffService.findOneByUid(params.getUid());
        PurchaseOrder purchaseOrder = purchaseOrderService.takeOver(params.getPurchaseSn(), staff);
        List<Logistics> list = logisticsService.shopIn(purchaseOrder);
        // if type is battery, insert into battery table
        if (purchaseOrder.getProduct().getType() == ProductType.BATTERY) {
            batteryService.shopIn(list);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("purchaseOrder", purchaseOrder);
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }
}
