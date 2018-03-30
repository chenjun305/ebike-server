package com.ecgobike.controller;

import com.ecgobike.entity.EBike;
import com.ecgobike.entity.Order;
import com.ecgobike.entity.ProductEBike;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.EBikeService;
import com.ecgobike.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/20.
 */
@RestController
@RequestMapping("/ebike")
public class AdminEBikeController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    OrderService orderService;

    @RequestMapping("/list")
    public MessageDto list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<EBike> ebikes = eBikeService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("ebikes", ebikes);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/product/list")
    public MessageDto productList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<ProductEBike> ebikeProducts = eBikeService.findAllProducts(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("ebikeProducts", ebikeProducts);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/sale/list")
    public MessageDto sellList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Order> saleList = orderService.findAllSall(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("saleList", saleList);
        return MessageDto.responseSuccess(data);
    }
}
