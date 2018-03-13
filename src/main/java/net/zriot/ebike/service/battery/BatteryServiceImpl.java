package net.zriot.ebike.service.battery;

import net.zriot.ebike.model.battery.Battery;
import net.zriot.ebike.model.ebike.EBike;
import net.zriot.ebike.repository.battery.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/12.
 */
@Service
public class BatteryServiceImpl implements BatteryService {
    @Autowired
    BatteryRepository batteryRepository;

    @Override
    public Battery findOneBySn(String sn) {
        return batteryRepository.findOneBySn(sn);
    }

    @Override
    public Battery changeToEBike(Battery battery, EBike eBike) {
        battery.setEbikeSn(eBike.getSn());
        battery.setUid(eBike.getUid());
        battery.setUpdateTime(LocalDateTime.now());
        return batteryRepository.save(battery);
    }
}
