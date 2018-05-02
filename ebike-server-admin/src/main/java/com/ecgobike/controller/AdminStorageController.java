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
import com.ecgobike.pojo.response.LogisticsVO;
import com.ecgobike.pojo.response.ProductVO;
import com.ecgobike.pojo.response.ShopVO;
import com.ecgobike.service.LogisticsService;
import com.ecgobike.service.ProductService;
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

import java.util.*;

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

    @Autowired
    Mapper mapper;

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

        // check if has duplicate sn
        List<String> snList = params.getSnList();
        Set<String> set = new HashSet<>(snList);
        if (set.size() < snList.size()) {
            throw new GException(ErrorConstants.ERR_DUPPLICATE_SN);
        }
        if (snList.size() != purchaseOrder.getPermitNum()) {
            throw new GException(ErrorConstants.SN_NUM_NOT_EQUAL_PURCHASE);
        }
        logisticsService.out(purchaseOrder, snList);
        purchaseOrderService.departure(purchaseOrder, params.getUid());

        return AppResponse.responseSuccess();
    }

    @RequestMapping("/out/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse outList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                                          Pageable pageable
    ) {
        Page<Logistics> list = logisticsService.findAllByStatus(LogisticsStatus.TRANSIT, pageable);
        Page<LogisticsVO> logisticsVOPage = list.map(logistics -> mapper.map(logistics, LogisticsVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("list", logisticsVOPage);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/in")
    @AuthRequire(Auth.ADMIN)
    public AppResponse in(StorageInParams params) throws GException {
        Product product = productService.getOne(params.getProductId());
        if (product == null) {
            throw new GException(ErrorConstants.NOT_EXIST_PRODUCT);
        }
        logisticsService.in(product, params.getSnList());
        return AppResponse.responseSuccess();
    }

    @RequestMapping("/in/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse inList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Logistics> list = logisticsService.findAll(pageable);
        Page<LogisticsVO> logisticsVOPage = list.map(logistics -> mapper.map(logistics, LogisticsVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("list", logisticsVOPage);
        return AppResponse.responseSuccess(data);
    }
}
