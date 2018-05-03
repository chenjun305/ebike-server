package com.ecgobike.task;

import com.ecgobike.entity.Shop;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/5/3.
 */
@Component
public class ShopCountBatteryTask {

    @Autowired
    ShopService shopService;

    @Autowired
    BatteryService batteryService;

    @Scheduled(fixedRate = 1000 * 60 * 5)
    public void countAvailableBatteryForAllShops() {
        List<Shop> shopList = shopService.findAll();
        for (Shop shop : shopList) {
            long batteryAvailable = batteryService.countStockInShop(shop.getId());
            shop.setBatteryAvailable((int)batteryAvailable);
            shop.setUpdateTime(LocalDateTime.now());
        }
        shopService.saveAll(shopList);
    }
}
