package net.zriot.ebike.service;

import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.entity.*;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/12.
 */
public interface BatteryService {
    Battery findOneBySn(String sn);
    List<Battery> findAll();
    List<ProductBattery> findAllProducts();
    LendBattery lend(EBike eBike, Battery battery);
    LendBattery returnBattery(Staff staff, Battery battery) throws GException;
}
