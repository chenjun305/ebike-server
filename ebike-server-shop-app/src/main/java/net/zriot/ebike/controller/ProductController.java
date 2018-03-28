package net.zriot.ebike.controller;

import net.zriot.ebike.pojo.response.BatteryVO;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.pojo.response.EBikeVO;
import net.zriot.ebike.service.ProductBatteryService;
import net.zriot.ebike.service.ProductEBikeService;
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
public class ProductController {

    @Autowired
    ProductBatteryService productBatteryService;
    @Autowired
    ProductEBikeService productEBikeService;

    @RequestMapping("/list")
    public MessageDto list(){
        Map<String, Object> data = new HashMap<>();
        data.put("ebikeList", productEBikeService.findAll());
        data.put("batteryList", productBatteryService.findAll());
        return MessageDto.responseSuccess(data);
    }
}
