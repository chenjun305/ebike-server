package com.ecgobike.controller;

import com.ecgobike.entity.EBike;
import com.ecgobike.entity.ProductEBike;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    EBikeService eBikeService;

    @RequestMapping("/list")
    public MessageDto list() {
        List<EBike> ebikes = eBikeService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", ebikes);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/product/list")
    public MessageDto productList() {
        List<ProductEBike> ebikeProducts = eBikeService.findAllProducts();
        Map<String, Object> data = new HashMap<>();
        data.put("ebikeProducts", ebikeProducts);
        return MessageDto.responseSuccess(data);
    }
}
