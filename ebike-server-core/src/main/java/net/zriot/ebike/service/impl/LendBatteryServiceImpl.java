package net.zriot.ebike.service.impl;

import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.entity.Battery;
import net.zriot.ebike.entity.EBike;
import net.zriot.ebike.entity.LendBattery;
import net.zriot.ebike.repository.LendBatteryRepository;
import net.zriot.ebike.service.LendBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Service
public class LendBatteryServiceImpl implements LendBatteryService {

    @Autowired
    LendBatteryRepository lendBatteryRepository;

    @Override
    public LendBattery save(LendBattery lendBattery) {
        return lendBatteryRepository.save(lendBattery);
    }

    @Override
    public LendBattery findUnreturn(String batterySn) {
        return lendBatteryRepository.findOneByBatterySnAndStatus(batterySn, (byte)0);
    }
}
