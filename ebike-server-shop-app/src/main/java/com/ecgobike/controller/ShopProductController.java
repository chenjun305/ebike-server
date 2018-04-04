package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.Product;
import com.ecgobike.pojo.response.BatteryProductVO;
import com.ecgobike.pojo.response.EBikeProductVO;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/23.
 */
@RestController
@RequestMapping("/product")
public class ShopProductController {

    @Autowired
    ProductService productService;

    @RequestMapping("/list")
    @AuthRequire(Auth.STAFF)
    public MessageDto list(){
        Map<String, Object> data = new HashMap<>();
        List<EBikeProductVO> ebikeProducts = new ArrayList<>();
        List<BatteryProductVO> batteryProducts = new ArrayList<>();
        List<Product> productList = productService.findAllProducts();
        for (Product product : productList) {
            if (product.getType() == ProductType.EBIKE) {
                EBikeProductVO eBikeProductVO = new EBikeProductVO();
                eBikeProductVO.setProductId(product.getId());
                eBikeProductVO.setModel(product.getModel());
                eBikeProductVO.setColor(product.getColor());
                eBikeProductVO.setIconUrl(product.getIconUrl());
                eBikeProductVO.setSellNum(25);
                eBikeProductVO.setStockNum(88);
                ebikeProducts.add(eBikeProductVO);
            } else if (product.getType() == ProductType.BATTERY) {
                BatteryProductVO batteryProductVO = new BatteryProductVO();
                batteryProductVO.setProductId(product.getId());
                batteryProductVO.setType(product.getModel());
                batteryProductVO.setIconUrl(product.getIconUrl());
                batteryProductVO.setStockNum(77);
                batteryProducts.add(batteryProductVO);
            }

        }
        data.put("ebikeProducts", ebikeProducts);
        data.put("batteryProducts", batteryProducts);
        return MessageDto.responseSuccess(data);
    }
}
