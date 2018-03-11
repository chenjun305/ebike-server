package net.zriot.ebike.controller.ebike;

import net.zriot.ebike.common.annotation.AuthRequire;
import net.zriot.ebike.common.enums.Auth;
import net.zriot.ebike.model.ebike.EBike;
import net.zriot.ebike.model.shop.Shop;
import net.zriot.ebike.pojo.request.AuthParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.ebike.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    EBikeService eBikeService;

    @PostMapping("/list")
    @AuthRequire(Auth.LOGIN)
    public MessageDto list(AuthParams authParams) {
        List<EBike> ebikes =  eBikeService.findAllByUid(authParams.getUid());
        Map<String, Object> data = new HashMap<>();
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
