package net.zriot.ebike.service;

import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.entity.EBike;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/12.
 */
public interface BatteryService {
    Battery findOneBySn(String sn);
    Battery changeToEBike(Battery battery, EBike eBike);
    Battery save(Battery battery);
    List<Battery> findAll();
}
