package net.zriot.ebike.service;

import net.zriot.ebike.entity.ProductBattery;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/28.
 */
public interface ProductBatteryService {
    List<ProductBattery> findAll();
}
