package net.zriot.ebike.controller.battery;

import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
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
}
