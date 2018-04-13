package com.ecgobike.controller;

import com.ecgobike.entity.Shop;
import com.ecgobike.pojo.request.ShopParams;
import com.ecgobike.service.ShopService;
import com.ecgobike.pojo.response.AppResponse;
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
 * Created by ChenJun on 2018/3/20.
 */
@RestController
@RequestMapping("/shop")
public class AdminShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/create")
    public AppResponse create(ShopParams params) {
        Shop shop = new Shop();
        shop.setName(params.getName());
        shop.setTel(params.getTel());
        shop.setAddress(params.getAddress());
        shop.setDescription(params.getDescription());
        shop.setLatitude(params.getLatitude());
        shop.setLongitude(params.getLongitude());
        shop.setOpenTime(params.getOpenTime());
        shop.setBatteryAvailable(0);
        shop.setStatus((byte)1);
        Shop newShop = shopService.create(shop);
        Map<String, Object> data = new HashMap<>();
        data.put("shop", newShop);
        return AppResponse.responseSuccess(data);
    }

    @PostMapping("/list")
    public AppResponse list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Shop> shops = shopService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("shops", shops);
        return AppResponse.responseSuccess(data);
    }
}
