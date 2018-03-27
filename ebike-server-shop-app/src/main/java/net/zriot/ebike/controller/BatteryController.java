package net.zriot.ebike.controller;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.entity.battery.Battery;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.battery.BatteryService;
import net.zriot.ebike.service.ebike.EBikeService;
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
    public MessageDto returnBattery(String batterySn) throws GException {
        Battery battery = batteryService.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }

        EBike eBike = eBikeService.findOneBySn(battery.getEbikeSn());
        eBike.setBatterySn(null);
        eBike.setUpdateTime(LocalDateTime.now());
        eBikeService.save(eBike);

        battery.setEbikeSn(null);
        battery.setUid(null);
        battery.setUpdateTime(LocalDateTime.now());
        batteryService.save(battery);


        return MessageDto.responseSuccess();
    }
}
