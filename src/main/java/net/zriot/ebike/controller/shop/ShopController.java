package net.zriot.ebike.controller.shop;

import net.zriot.ebike.model.shop.Shop;
import net.zriot.ebike.pojo.response.MessageDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/shop")
public class ShopController {

    @PostMapping("/near")
    public MessageDto list(String latitude, String longitude) {
        Map<String, Object> data = new HashMap<>();
        List<Shop> shops = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            Shop shop = new Shop();
            shop.setId(i+1);
            shop.setName("Ebike Shop NO." + i);
            shop.setTel("13829780305");
            shop.setAddress("NO 1, tianhe raod, Guangzhou");
            shop.setOpenTime("9:00 - 18:00");
            shop.setDescription("Very Good Shop");
            shop.setLatitude("23.13958429918658");
            shop.setLongitude("113.3377031609416");
            shop.setBatteryAvailable(12);
            shop.setStatus((byte)1);
            shops.add(shop);
        }
        data.put("shops", shops);
        return MessageDto.responseSuccess(data);

    }
}
