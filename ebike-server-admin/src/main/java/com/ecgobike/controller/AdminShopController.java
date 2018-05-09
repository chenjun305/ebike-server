package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.Shop;
import com.ecgobike.pojo.request.ShopParams;
import com.ecgobike.pojo.response.ShopVO;
import com.ecgobike.service.ShopService;
import com.ecgobike.pojo.response.AppResponse;
import org.dozer.Mapper;
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

    @Autowired
    Mapper mapper;

    @RequestMapping("/create")
    @AuthRequire(Auth.ADMIN)
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
        Shop newShop = shopService.save(shop);
        ShopVO shopVO = mapper.map(newShop, ShopVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("shop", shopVO);
        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Shop> shops = shopService.findAll(pageable);
        Page<ShopVO> shopVOPage = shops.map(shop -> mapper.map(shop, ShopVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("shops", shopVOPage);
        return AppResponse.responseSuccess(data);
    }
}
