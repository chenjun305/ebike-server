package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
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
        data.put("ebikeProducts", eBikeService.findAllProducts());
        data.put("batteryProducts", batteryService.findAllProducts());
        return MessageDto.responseSuccess(data);
    }
}
