package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.Logistics;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.Product;
import com.ecgobike.pojo.response.EBikeProductVO;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.LogisticsService;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.ProductService;
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

    @RequestMapping("/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Logistics> ebikes = logisticsService.findAllByType(ProductType.EBIKE, pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", ebikes);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/product/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse productList() {
        List<Product> productList = productService.findByType(ProductType.EBIKE);
        List<EBikeProductVO> ebikeProducts = new ArrayList<>();
        for(Product product : productList) {
            EBikeProductVO eBikeProductVO = new EBikeProductVO();
            eBikeProductVO.setProductId(product.getId());
            eBikeProductVO.setModel(product.getModel());
            eBikeProductVO.setColor(product.getColor());
            eBikeProductVO.setIconUrl(product.getIconUrl());
            eBikeProductVO.setPrice(product.getPrice());
            eBikeProductVO.setCurrency(product.getCurrency());
            long sellNum = paymentOrderService.countProductSellOrders(product.getId());
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
        Map<String, Object> data = new HashMap<>();
        data.put("saleList", saleList);
        return AppResponse.responseSuccess(data);
    }
}
