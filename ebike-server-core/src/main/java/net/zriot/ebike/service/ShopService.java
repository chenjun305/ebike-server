package net.zriot.ebike.service;

import net.zriot.ebike.entity.Shop;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface ShopService {
    List<Shop> near(Double latitude, Double longitude);
    List<Shop> findAll();
    Shop create(Shop shop);
}
