package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.pojo.request.PurchasePermitParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.ProductVO;
import com.ecgobike.pojo.response.PurchaseOrderVO;
import com.ecgobike.pojo.response.ShopVO;
import com.ecgobike.service.PurchaseOrderService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/8.
 */
@RestController
@RequestMapping("purchase")
public class AdminPurchaseController {

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/list/require")
    @AuthRequire(Auth.ADMIN)
    public AppResponse listRequire(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<PurchaseOrder> purchaseRequireOrders = purchaseOrderService.findAllRequire(pageable);
        Page<PurchaseOrderVO> purchaseOrderVOPage = purchaseRequireOrders.map(
                purchaseOrder -> mapper.map(purchaseOrder, PurchaseOrderVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("purchaseRequireOrders", purchaseOrderVOPage);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/list/permit")
    @AuthRequire(Auth.ADMIN)
    public AppResponse listPermit(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<PurchaseOrder> purchasePermitOrders = purchaseOrderService.findAllPermit(pageable);
        Page<PurchaseOrderVO> purchaseOrderVOPage = purchasePermitOrders.map(
                purchaseOrder -> mapper.map(purchaseOrder, PurchaseOrderVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("purchasePermitOrders", purchaseOrderVOPage);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/permit")
    @AuthRequire(Auth.ADMIN)
    public AppResponse permit(PurchasePermitParams params) throws GException {
        PurchaseOrder purchaseOrder = purchaseOrderService.permit(params.getPurchaseSn(), params.getPermitNum(), params.getUid());

        Map<String, Object> data = new HashMap<>();
        PurchaseOrderVO purchaseOrderVO = mapper.map(purchaseOrder, PurchaseOrderVO.class);

        data.put("purchaseOrder", purchaseOrderVO);
        return AppResponse.responseSuccess(data);
    }
}
