package net.zriot.ebike.service.impl;

import net.zriot.ebike.common.constant.ErrorConstants;
import net.zriot.ebike.common.exception.GException;
import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.entity.*;
import net.zriot.ebike.repository.BatteryRepository;
import net.zriot.ebike.repository.LendBatteryRepository;
import net.zriot.ebike.repository.ProductBatteryRepository;
import net.zriot.ebike.service.BatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/3/12.
 */
@Service
public class BatteryServiceImpl implements BatteryService {
    @Autowired
    BatteryRepository batteryRepository;

    @Autowired
    ProductBatteryRepository productBatteryRepository;

    @Autowired
    LendBatteryRepository lendBatteryRepository;

    @Override
    public Battery findOneBySn(String sn) {
        return batteryRepository.findOneBySn(sn);
    }

    @Override
    public List<Battery> findAll() {
        return batteryRepository.findAll();
    }

    @Override
    public List<ProductBattery> findAllProducts() {
        return productBatteryRepository.findAll();
    }

    @Override
    public LendBattery lend(EBike eBike, Battery battery) {
        battery.setShopId(null);
        battery.setEbikeSn(eBike.getSn());
        battery.setUpdateTime(LocalDateTime.now());
        batteryRepository.save(battery);

        LendBattery lendBattery = new LendBattery();
        lendBattery.setSn(IdGen.genOrderSn());
        lendBattery.setBatterySn(battery.getSn());
        lendBattery.setEbikeSn(eBike.getSn());
        lendBattery.setLendTime(LocalDateTime.now());
        lendBattery.setUid(eBike.getUid());
        lendBattery.setLendShopId(battery.getShopId());
        lendBattery.setStatus((byte)0);
        return lendBatteryRepository.save(lendBattery);
    }

    @Override
    public LendBattery returnBattery(Staff staff, Battery battery) throws GException {
        battery.setEbikeSn(null);
        // TODO set right shopID
        battery.setShopId(staff.getShopId());
        battery.setUpdateTime(LocalDateTime.now());
        batteryRepository.save(battery);

        LendBattery lendBattery = lendBatteryRepository.findOneByBatterySnAndStatus(battery.getSn(), (byte)0);;
        if (lendBattery == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }
        lendBattery.setReturnTime(LocalDateTime.now());
        lendBattery.setReturnShopId(staff.getShopId());
        lendBattery.setReturnStaffUid(staff.getUid());
        lendBattery.setStatus((byte)1);
        lendBattery.setUpdateTime(LocalDateTime.now());
        return lendBatteryRepository.save(lendBattery);
    }
}
