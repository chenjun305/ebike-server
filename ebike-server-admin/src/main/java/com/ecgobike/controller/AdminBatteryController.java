package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.entity.LendBattery;
import com.ecgobike.pojo.request.LendHistoryParams;
import com.ecgobike.pojo.response.BatteryVO;
import com.ecgobike.pojo.response.LendBatteryVO;
import com.ecgobike.service.BatteryService;
import com.ecgobike.entity.Battery;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.service.LendBatteryService;
import com.ecgobike.service.ProductService;
import org.dozer.Mapper;
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

    @Autowired
    Mapper mapper;

    @RequestMapping("/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse list(
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<Battery> batteries = batteryService.findAll(pageable);
        Page<BatteryVO> voPage = batteries.map(battery -> mapper.map(battery, BatteryVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("batteries", voPage);

        return AppResponse.responseSuccess(data);
    }

    @RequestMapping("/lend/list")
    @AuthRequire(Auth.ADMIN)
    public AppResponse lendList(LendHistoryParams params,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.DESC)
                    Pageable pageable
    ) {
        Page<LendBattery> lendHistory = lendBatteryService.findAllLendHistoryByBatterySn(params.getBatterySn(), pageable);
        Page<LendBatteryVO> lendBatteryVOPage = lendHistory.map(lendBattery -> mapper.map(lendBattery, LendBatteryVO.class));
        Map<String, Object> data = new HashMap<>();
        data.put("lendHistory", lendBatteryVOPage);

        return AppResponse.responseSuccess(data);
    }
}
