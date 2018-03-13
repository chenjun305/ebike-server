package net.zriot.ebike.service.battery;

import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.model.battery.Battery;
import net.zriot.ebike.model.ebike.EBike;

/**
 * Created by ChenJun on 2018/3/12.
 */
public interface BatteryService {
    Battery findOneBySn(String sn);
    Battery changeToEBike(Battery battery, EBike eBike);
}
