package com.ecgobike.controller;

import com.ecgobike.common.annotation.AuthRequire;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.Auth;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Battery;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.LendBattery;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.LendBatteryParams;
import com.ecgobike.pojo.response.MessageDto;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.EBikeService;
import com.ecgobike.service.LendBatteryService;
import com.ecgobike.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
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

    @PostMapping("/lend")
    @AuthRequire(Auth.USER)
    public MessageDto lend(LendBatteryParams params) throws GException {
        EBike eBike = eBikeService.canLendBattery(params.getEbikeSn());
        if (eBike.getUid() == null || !eBike.getUid().equals(params.getUid())) {
            throw new GException(ErrorConstants.NOT_YOUR_EBIKE);
        }

        // check for battery
        Battery battery = batteryService.findOneBySn(params.getBatterySn());
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() != null) {
            throw new GException(ErrorConstants.NOT_RETURNED_BATTERY);
        }

        LendBattery lendBattery = lendBatteryService.lend(eBike, battery, null);
        batteryService.lend(eBike, battery);

        Map<String, Object> data = new HashMap<>();
        data.put("paidAmount", 0);
        data.put("lendBattery", lendBattery);
        data.put("expireDate", eBike.getExpireDate());
        return MessageDto.responseSuccess(data);
    }
}
