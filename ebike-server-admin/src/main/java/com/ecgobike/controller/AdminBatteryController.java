package com.ecgobike.controller;

import com.ecgobike.entity.LendBattery;
import com.ecgobike.entity.ProductBattery;
import com.ecgobike.service.BatteryService;
import com.ecgobike.entity.Battery;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/21.
 */
@RestController
@RequestMapping("/battery")
public class AdminBatteryController {

    @Autowired
    BatteryService batteryService;

    @RequestMapping("/list")
    public MessageDto list() {
        List<Battery> batteries = batteryService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("batteries", batteries);

        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/product/list")
    public MessageDto productList() {
        List<ProductBattery> batteryProducts = batteryService.findAllProducts();
        Map<String, Object> data = new HashMap<>();
        data.put("batteryProducts", batteryProducts);

        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/lend/list")
    public MessageDto lendList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable) {
        Page<LendBattery> lendHistory = batteryService.findAllLendHistory(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("lendHistory", lendHistory);

        return MessageDto.responseSuccess(data);
    }
}
