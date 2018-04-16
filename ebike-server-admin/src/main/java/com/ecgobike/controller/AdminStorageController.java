package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.LogisticsStatus;
import com.ecgobike.common.enums.PurchaseOrderStatus;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Logistics;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.PurchaseOrder;
import com.ecgobike.pojo.request.StorageInParams;
import com.ecgobike.pojo.request.StorageOutParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.LogisticsService;
import com.ecgobike.service.ProductService;
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
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/4/8.
 */
@RestController
@RequestMapping("/storage")
public class AdminStorageController {

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    PurchaseOrderService purchaseOrderService;

    @Autowired
    ProductService productService;

    @PostMapping("/out")
    @AuthRequire(Auth.ADMIN)
    public AppResponse out(StorageOutParams params) throws GException {
        PurchaseOrder purchaseOrder = purchaseOrderService.findOneBySn(params.getPurchaseSn());
        if (purchaseOrder == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PURCHASE_ORDER);
        }
        if (purchaseOrder.getStatus() != PurchaseOrderStatus.PERMIT) {
            throw new GException(ErrorConstants.NOT_PERMIT_PURCHASE_ORDER);
        }
        List<Logistics> list = logisticsService.out(purchaseOrder, params.getSnList());
        purchaseOrderService.departure(purchaseOrder);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/out/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse outList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                                          Pageable pageable
    ) {
        Page<Logistics> list = logisticsService.findAllByStatus(LogisticsStatus.TRANSIT, pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/in")
    @AuthRequire(Auth.ADMIN)
    public AppResponse in(StorageInParams params) throws GException {
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        List<Logistics> list = logisticsService.in(product, params.getSnList());
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/in/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse inList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Logistics> list = logisticsService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("list", list);
        return AppResponse.responseSuccess(data);
    }
}
