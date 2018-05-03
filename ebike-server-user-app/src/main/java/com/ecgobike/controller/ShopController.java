package com.ecgobike.controller;

import com.ecgobike.entity.Shop;
import com.ecgobike.pojo.response.ShopVO;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.ShopService;
import com.ecgobike.pojo.response.AppResponse;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    Mapper mapper;

    @PostMapping("/near")
    public AppResponse list(Double latitude, Double longitude) {
        List<Shop> shops = shopService.near(latitude, longitude);
        List<ShopVO> shopVOList = shops.stream()
                .map(shop -> mapper.map(shop, ShopVO.class))
                .collect(Collectors.toList()
        );
        Map<String, Object> data = new HashMap<>();
        data.put("shops", shopVOList);
        return AppResponse.responseSuccess(data);

    }
}
