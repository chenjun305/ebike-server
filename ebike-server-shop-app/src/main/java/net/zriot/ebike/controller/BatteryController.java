package net.zriot.ebike.controller;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.entity.EBike;
import net.zriot.ebike.entity.LendBattery;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.BatteryService;
import net.zriot.ebike.service.EBikeService;
import net.zriot.ebike.service.LendBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/27.
 */
@RestController
@RequestMapping("/battery")
public class BatteryController {

    @Autowired
    BatteryService batteryService;

    @Autowired
    EBikeService eBikeService;

    @Autowired
    LendBatteryService lendBatteryService;

    @RequestMapping("/info")
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
    public MessageDto returnBattery(String batterySn, AuthParams authParams) throws GException {
        Battery battery = batteryService.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }

        LendBattery lendBattery = lendBatteryService.findUnreturn(batterySn);
        if (lendBattery == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }
        lendBattery.setReturnTime(LocalDateTime.now());
        lendBattery.setReturnShopId(1L);
        lendBattery.setReturnStaffUid(authParams.getUid());
        lendBattery.setStatus((byte)1);
        lendBattery.setUpdateTime(LocalDateTime.now());
        lendBattery = lendBatteryService.save(lendBattery);

        battery.setEbikeSn(null);
        // TODO set right shopID
        battery.setShopId(1L);
        battery.setUpdateTime(LocalDateTime.now());
        batteryService.save(battery);

        Map<String, Object> data = new HashMap<>();
        data.put("order", lendBattery);

        return MessageDto.responseSuccess(data);
    }
}
