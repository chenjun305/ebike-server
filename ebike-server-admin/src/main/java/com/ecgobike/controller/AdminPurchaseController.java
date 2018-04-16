package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.pojo.request.PurchasePermitParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.PurchaseOrderService;
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
 * Created by ChenJun on 2018/4/8.
 */
@RestController
@RequestMapping("purchase")
public class AdminPurchaseController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @RequestMapping("/list/require")
    @AuthRequire(Auth.ADMIN)
    public AppResponse listRequire(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<PurchaseOrder> purchaseRequireOrders = purchaseOrderService.findAllRequire(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("purchaseRequireOrders", purchaseRequireOrders);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/list/permit")
    @AuthRequire(Auth.ADMIN)
    public AppResponse listPermit(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<PurchaseOrder> purchasePermitOrders = purchaseOrderService.findAllPermit(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("purchasePermitOrders", purchasePermitOrders);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/permit")
    @AuthRequire(Auth.ADMIN)
    public AppResponse permit(PurchasePermitParams params) throws GException {
        PurchaseOrder purchaseOrder = purchaseOrderService.permit(params.getPurchaseSn(), params.getPermitNum());
        Map<String, Object> data = new HashMap<>();
        data.put("purchaseOrder", purchaseOrder);
        return AppResponse.responseSuccess(data);
    }
}
