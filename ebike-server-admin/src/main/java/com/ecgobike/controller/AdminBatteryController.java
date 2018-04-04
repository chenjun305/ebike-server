package com.ecgobike.controller;

import com.ecgobike.common.enums.ProductType;
import com.ecgobike.entity.LendBattery;
import com.ecgobike.entity.Product;
import com.ecgobike.pojo.response.BatteryProductVO;
import com.ecgobike.pojo.response.EBikeProductVO;
import com.ecgobike.service.BatteryService;
import com.ecgobike.entity.Battery;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.LendBatteryService;
import com.ecgobike.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
 * Created by ChenJun on 2018/3/21.
 */
@RestController
@RequestMapping("/battery")
public class AdminBatteryController {

    @Autowired
    BatteryService batteryService;

    @Autowired
    LendBatteryService lendBatteryService;

    @Autowired
    ProductService productService;

    @RequestMapping("/list")
    public MessageDto list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Battery> batteries = batteryService.findAll(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("batteries", batteries);

        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/product/list")
    public MessageDto productList() {
        List<BatteryProductVO> batteryProducts = new ArrayList<>();
        List<Product> productList = productService.findByType(ProductType.BATTERY);
        for (Product product :
                productList) {
            BatteryProductVO batteryProductVO = new BatteryProductVO();
            batteryProductVO.setProductId(product.getId());
            batteryProductVO.setType(product.getModel());
            batteryProductVO.setIconUrl(product.getIconUrl());
            batteryProductVO.setStockNum(77);
            batteryProducts.add(batteryProductVO);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("batteryProducts", batteryProducts);

        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/lend/list")
    public MessageDto lendList(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<LendBattery> lendHistory = lendBatteryService.findAllLendHistory(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("lendHistory", lendHistory);

        return MessageDto.responseSuccess(data);
    }
}
