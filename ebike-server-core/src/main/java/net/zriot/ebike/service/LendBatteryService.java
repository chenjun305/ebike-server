package net.zriot.ebike.service;

import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.entity.EBike;
import net.zriot.ebike.entity.LendBattery;

/**
 * Created by ChenJun on 2018/3/27.
 */
public interface LendBatteryService {
    LendBattery save(LendBattery lendBattery);
    LendBattery findUnreturn(String batterySn);
}
