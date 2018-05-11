package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.Shop;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.ShopVO;
import com.ecgobike.service.ShopService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by ChenJun on 2018/5/11.
 */
@RestController
@RequestMapping("/booking")
public class AdminBookingController {

    @Autowired
    ShopService shopService;

    @Autowired
    Mapper mapper;

    @RequestMapping("/data")
    @AuthRequire(Auth.ADMIN)
    public AppResponse data() {
        List<Shop> shopList = shopService.findAll();
        List<ShopVO> shopVOList = shopList.stream()
                .map(shop -> mapper.map(shop, ShopVO.class))
                .collect(Collectors.toList());
        Map<String, Object> data = new HashMap<>();
        data.put("list", shopVOList);
        return AppResponse.responseSuccess(data);
    }
}
