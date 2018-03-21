package net.zriot.ebike.controller.shop;

import net.zriot.ebike.entity.shop.Shop;
import net.zriot.ebike.pojo.request.ShopParams;
import net.zriot.ebike.pojo.response.MessageDto;
import net.zriot.ebike.service.shop.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/20.
 */
@RestController
@RequestMapping("/shop")
public class AdminShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/create")
    public MessageDto create(ShopParams params) {
        Shop shop = new Shop();
        shop.setName(params.getName());
        shop.setTel(params.getTel());
        shop.setAddress(params.getAddress());
        shop.setDescription(params.getDescription());
        shop.setLatitude(params.getLatitude());
        shop.setLongitude(params.getLongitude());
        shop.setOpenTime(params.getOpenTime());
        shop.setBatteryAvailable(0);
        shop.setStatus((byte)1);
        Shop newShop = shopService.create(shop);
        Map<String, Object> data = new HashMap<>();
        data.put("shop", newShop);
        return MessageDto.responseSuccess(data);
    }

    @PostMapping("/list")
    public MessageDto list() {
        List<Shop> shops = shopService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("shops", shops);
        return MessageDto.responseSuccess(data);
    }
}
