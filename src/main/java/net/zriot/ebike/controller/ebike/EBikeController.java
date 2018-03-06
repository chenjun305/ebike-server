package net.zriot.ebike.controller.ebike;

import net.zriot.ebike.model.ebike.EBike;
import net.zriot.ebike.model.shop.Shop;
import net.zriot.ebike.pojo.response.MessageDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ebike")
public class EBikeController {

    @PostMapping("/list")
    public MessageDto list() {
        Map<String, Object> data = new HashMap<>();
        List<EBike> ebikes = new ArrayList<>();
        EBike ebike = new EBike();
        ebike.setId(111);
        ebike.setSn("311400XXYY");
        ebike.setName("EBike Model - Blue");
        ebike.setNote("High quality");
        ebike.setShopId(11);
        ebike.setBuyTime(LocalDateTime.now());
        ebike.setIsMembership((byte)1);
        ebike.setMembership(20);
        ebike.setMonthFee(7);
        ebike.setMonthStartDate(LocalDate.now());
        ebike.setMonthEndDate(LocalDate.now().plusMonths(1));
        ebike.setBatteryId(112);
        ebike.setStatus(1);
        ebikes.add(ebike);
        data.put("ebikes", ebikes);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/join")
    public MessageDto join() {
        Map<String, Object> data = new HashMap<>();
        data.put("balance", 99);
        data.put("orderSn", "2018030600001");
        data.put("orderTime", LocalDateTime.now());
        data.put("monthStartDate", LocalDate.now());
        data.put("monthEndDate", LocalDate.now().plusMonths(1));
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/renew")
    public MessageDto renew() {
        Map<String, Object> data = new HashMap<>();
        data.put("balance", 92);
        data.put("orderSn", "2018030600001");
        data.put("orderTime", LocalDateTime.now());
        data.put("monthStartDate", LocalDate.now());
        data.put("monthEndDate", LocalDate.now().plusMonths(1));
        return MessageDto.responseSuccess(data);
    }

}
