package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Battery;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.LendBattery;
import com.ecgobike.pojo.request.LendBatteryParams;
import com.ecgobike.pojo.response.AppResponse;
import com.ecgobike.pojo.response.EBikeVO;
import com.ecgobike.pojo.response.LendBatteryVO;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.EBikeService;
import com.ecgobike.service.LendBatteryService;
import com.ecgobike.service.UserService;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/battery")
public class BatteryController {

    @Autowired
    EBikeService eBikeService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    UserService userService;

    @Autowired
    LendBatteryService lendBatteryService;

    @Autowired
    Mapper mapper;

    @PostMapping("/lend")
    @AuthRequire(Auth.USER)
    public AppResponse lend(LendBatteryParams params) throws GException {
        String ebikeSn = params.getEbikeSn();
        EBike eBike = eBikeService.canLendBattery(ebikeSn);
        if (eBike.getUid() == null || !eBike.getUid().equals(params.getUid())) {
            throw new GException(ErrorConstants.NOT_YOUR_EBIKE);
        }

        // check for battery
        Battery battery = batteryService.canLend(params.getBatterySn(), ebikeSn,null);

        LendBattery lendBattery = lendBatteryService.lend(eBike, battery, null);
        batteryService.lend(eBike, battery);
        eBike = eBikeService.lendBattery(eBike);
        LendBatteryVO lendBatteryVO = mapper.map(lendBattery, LendBatteryVO.class);
        EBikeVO eBikeVO = mapper.map(eBike, EBikeVO.class);
        Map<String, Object> data = new HashMap<>();
        data.put("lendBattery", lendBatteryVO);
        data.put("ebike", eBikeVO);
        return AppResponse.responseSuccess(data);
    }
}
