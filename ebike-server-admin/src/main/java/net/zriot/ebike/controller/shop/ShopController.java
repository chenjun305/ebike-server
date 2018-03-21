package net.zriot.ebike.controller.shop;

import net.zriot.ebike.entity.shop.Shop;
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
public class ShopController {

    @Autowired
    ShopService shopService;

    @PostMapping("/create")
    public MessageDto create() {
        return MessageDto.responseSuccess();
    }

    @PostMapping("/list")
    public MessageDto list() {
        List<Shop> shops = shopService.findAll();
        Map<String, Object> data = new HashMap<>();
        data.put("shops", shops);
        return MessageDto.responseSuccess(data);
    }
}
