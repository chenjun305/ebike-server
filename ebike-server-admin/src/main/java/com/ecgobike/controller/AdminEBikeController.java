package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.Logistics;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.Product;
import com.ecgobike.pojo.response.EBikeProductVO;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.LogisticsVO;
import com.ecgobike.pojo.response.PaymentOrderVO;
import com.ecgobike.service.LogisticsService;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.ProductService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/20.
 */
@RestController
@RequestMapping("/ebike")
public class AdminEBikeController {

    @Autowired
    LogisticsService logisticsService;

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    ProductService productService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Logistics> logisticsPage = logisticsService.findAllByType(ProductType.EBIKE, pageable);
        Page<LogisticsVO> logisticsVOPage = logisticsPage.map(logistics -> mapper.map(logistics, LogisticsVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", logisticsVOPage);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/product/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse productList() {
        List<Product> productList = productService.findByType(ProductType.EBIKE);
        List<EBikeProductVO> ebikeProducts = new ArrayList<>();
        for(Product product : productList) {
            EBikeProductVO eBikeProductVO = mapper.map(product, EBikeProductVO.class);
            long sellNum = paymentOrderService.countProductSellOrders(product);
            eBikeProductVO.setSellNum(sellNum);
            long stockNum = logisticsService.countProductStock(product);
            eBikeProductVO.setStockNum(stockNum);
            ebikeProducts.add(eBikeProductVO);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("ebikeProducts", ebikeProducts);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/sale/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse sellList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<PaymentOrder> saleList = paymentOrderService.findAllSall(pageable);
        Page<PaymentOrderVO> paymentOrderVOPage = saleList.map(paymentOrder -> mapper.map(paymentOrder, PaymentOrderVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("saleList", paymentOrderVOPage);
        return AppResponse.responseSuccess(data);
    }
}
