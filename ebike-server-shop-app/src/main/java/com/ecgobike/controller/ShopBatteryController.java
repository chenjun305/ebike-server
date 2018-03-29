package com.ecgobike.controller;

import com.ecgobike.pojo.request.ReturnBatteryParams;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.StaffService;
import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Battery;
import com.ecgobike.entity.LendBattery;
import com.ecgobike.entity.Staff;
import com.ecgobike.pojo.response.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
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
        Battery battery = batteryService.findOneBySn(params.getBatterySn());
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }

        Staff staff = staffService.findOneByUid(params.getUid());
        LendBattery lendBattery = batteryService.returnBattery(staff, battery);


        Map<String, Object> data = new HashMap<>();
        data.put("order", lendBattery);

        return MessageDto.responseSuccess(data);
    }
}
