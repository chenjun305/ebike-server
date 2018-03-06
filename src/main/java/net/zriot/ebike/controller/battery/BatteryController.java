package net.zriot.ebike.controller.battery;

import net.zriot.ebike.pojo.response.MessageDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/battery")
public class BatteryController {

    @PostMapping("/change")
    public MessageDto change() {
        Map<String, Object> data = new HashMap<>();
        data.put("paidAmount", 0);
        data.put("balance", 92);
        data.put("orderSn", "2018030600001");
        data.put("orderTime", LocalDateTime.now());
        data.put("expireDate", LocalDate.now().plusMonths(1));
        return MessageDto.responseSuccess(data);
    }
}
