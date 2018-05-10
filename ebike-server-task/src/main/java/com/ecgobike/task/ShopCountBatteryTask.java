package com.ecgobike.task;

import com.ecgobike.entity.Shop;
import com.ecgobike.service.BatteryService;
import com.ecgobike.service.BookBatteryService;
import com.ecgobike.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by ChenJun on 2018/5/3.
 */
@Component
public class ShopCountBatteryTask {

    @Autowired
    ShopService shopService;

    @Autowired
    BatteryService batteryService;

    @Autowired
    BookBatteryService bookBatteryService;

    @Scheduled(fixedRate = 1000 * 60)
    public void countBatteryForAllShops() {
        // check if booking expire
        bookBatteryService.adjustStatus();
        List<Shop> list = shopService.findAll();
        List<Shop> saveList = list.stream().map(shop -> {
            Long batteryAvailable = batteryService.countStockInShop(shop.getId());
            Long batteryBooked = bookBatteryService.countBookNumInShop(shop.getId());
            shop.setBatteryAvailable(batteryAvailable.intValue());
            shop.setBatteryBooked(batteryBooked.intValue());
            shop.setUpdateTime(LocalDateTime.now());
            return shop;
        }).collect(Collectors.toList());

        shopService.saveAll(saveList);
    }
}
