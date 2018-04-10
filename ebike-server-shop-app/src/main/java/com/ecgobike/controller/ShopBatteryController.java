package com.ecgobike.controller;

import com.ecgobike.entity.*;
import com.ecgobike.pojo.request.LendBatteryParams;
import com.ecgobike.pojo.request.ReturnBatteryParams;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.EBikeService;
import com.ecgobike.service.LendBatteryService;
import com.ecgobike.service.StaffService;
import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/27.
 */
@RestController
@RequestMapping("/battery")
public class ShopBatteryController {

    @Autowired
    BatteryService batteryService;

    @Autowired
    StaffService staffService;

    @Autowired
    LendBatteryService lendBatteryService;

    @Autowired
    EBikeService eBikeService;

    @RequestMapping("/info")
    @AuthRequire(Auth.STAFF)
    public MessageDto info(String batterySn) throws GException {
        Battery battery = batteryService.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("battery", battery);
        return MessageDto.responseSuccess(data);
    }

    @RequestMapping("/return")
    @AuthRequire(Auth.STAFF)
    public MessageDto returnBattery(ReturnBatteryParams params) throws GException {
        Staff staff = staffService.findOneByUid(params.getUid());
        batteryService.returnBattery(staff, params.getBatterySn());
        LendBattery lendBattery = lendBatteryService.returnBattery(staff, params.getBatterySn());

        Map<String, Object> data = new HashMap<>();
        data.put("lendBattery", lendBattery);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/lend")
    @AuthRequire(Auth.STAFF)
    public MessageDto lend(LendBatteryParams params) throws GException {
        EBike eBike = eBikeService.canLendBattery(params.getEbikeSn());
        if (eBike.getUid() == null) {
            throw new GException(ErrorConstants.NOT_YOUR_EBIKE);
        }

        // check for battery
        Staff staff = staffService.findOneByUid(params.getUid());
        Battery battery = batteryService.canLend(params.getBatterySn(), staff);

        LendBattery lendBattery = lendBatteryService.lend(eBike, battery, params.getUid());
        batteryService.lend(eBike, battery);
        eBike = eBikeService.lendBattery(eBike);

        Map<String, Object> data = new HashMap<>();
        data.put("lendBattery", lendBattery);
        data.put("ebike", eBike);
        return MessageDto.responseSuccess(data);
    }
}
