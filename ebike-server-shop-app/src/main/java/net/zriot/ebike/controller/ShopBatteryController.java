package net.zriot.ebike.controller;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.entity.LendBattery;
import net.zriot.ebike.entity.Staff;
import net.zriot.ebike.pojo.request.ReturnBatteryParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.BatteryService;
import net.zriot.ebike.service.StaffService;
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
