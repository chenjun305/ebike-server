package net.zriot.ebike.service.shop;

import net.zriot.ebike.model.shop.Shop;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface ShopService {
    List<Shop> near(Double latitude, Double longitude);
}
