package net.zriot.ebike.controller.shop;

import net.zriot.ebike.entity.Shop;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/near")
    public MessageDto list(Double latitude, Double longitude) {
        List<Shop> shops = shopService.near(latitude, longitude);
        Map<String, Object> data = new HashMap<>();
        data.put("shops", shops);
        return MessageDto.responseSuccess(data);

    }
}
