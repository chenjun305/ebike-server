package com.ecgobike.controller;

import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.pojo.request.PurchaseDepartureParams;
import com.ecgobike.pojo.request.PurchasePermitParams;
import com.ecgobike.pojo.response.MessageDto;
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
    public MessageDto listRequire(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<PurchaseOrder> purchaseRequireOrders = purchaseOrderService.findAllRequire(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("purchaseRequireOrders", purchaseRequireOrders);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/list/permit")
    public MessageDto listPermit(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<PurchaseOrder> purchasePermitOrders = purchaseOrderService.findAllPermit(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("purchasePermitOrders", purchasePermitOrders);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/permit")
    public MessageDto permit(PurchasePermitParams params) throws GException {
        PurchaseOrder purchaseOrder = purchaseOrderService.permit(params.getPurchaseSn(), params.getPermitNum());
        Map<String, Object> data = new HashMap<>();
        data.put("purchaseOrder", purchaseOrder);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/departure")
    public MessageDto departure(PurchaseDepartureParams params) {
        return MessageDto.responseSuccess();
    }
}
