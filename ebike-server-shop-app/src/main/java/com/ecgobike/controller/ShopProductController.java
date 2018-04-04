package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.ProductBattery;
import com.ecgobike.entity.ProductEBike;
import com.ecgobike.pojo.response.BatteryProductVO;
import com.ecgobike.pojo.response.EBikeProductVO;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Created by ChenJun on 2018/3/23.
 */
@RestController
@RequestMapping("/product")
public class ShopProductController {

    @Autowired
    BatteryService batteryService;
    @Autowired
    EBikeService eBikeService;

    @RequestMapping("/list")
    @AuthRequire(Auth.STAFF)
    public MessageDto list(){
        Map<String, Object> data = new HashMap<>();
        List<EBikeProductVO> ebikeProducts = new ArrayList<>();
        List<BatteryProductVO> batteryProducts = new ArrayList<>();
        for (ProductEBike productEBike : eBikeService.findAllProducts()) {
            EBikeProductVO eBikeProductVO = new EBikeProductVO();
            eBikeProductVO.setProductId(productEBike.getId());
            eBikeProductVO.setModel(productEBike.getModel());
            eBikeProductVO.setColor(productEBike.getColor());
            eBikeProductVO.setIconUrl(productEBike.getIconUrl());
            eBikeProductVO.setSellNum(25);
            eBikeProductVO.setStockNum(88);
            ebikeProducts.add(eBikeProductVO);
        }
        for (ProductBattery productBattery : batteryService.findAllProducts()) {
            BatteryProductVO batteryProductVO = new BatteryProductVO();
            batteryProductVO.setProductId(productBattery.getId());
            batteryProductVO.setType(productBattery.getModel());
            batteryProductVO.setIconUrl(productBattery.getIconUrl());
            batteryProductVO.setStockNum(77);
            batteryProducts.add(batteryProductVO);
        }
        data.put("ebikeProducts", ebikeProducts);
        data.put("batteryProducts", batteryProducts);
        return MessageDto.responseSuccess(data);
    }
}
