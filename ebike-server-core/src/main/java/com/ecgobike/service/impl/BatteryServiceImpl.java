package com.ecgobike.service.impl;

import com.ecgobike.entity.*;
import com.ecgobike.repository.LendBatteryRepository;
import com.ecgobike.repository.ProductBatteryRepository;
import com.ecgobike.service.BatteryService;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @Override
    public Battery findOneBySn(String sn) {
        return batteryRepository.findOneBySn(sn);
    }

    @Override
    public Page<Battery> findAll(Pageable pageable) {
        return batteryRepository.findAll(pageable);
    }

    @Override
    public Page<ProductBattery> findAllProducts(Pageable pageable) {
        return productBatteryRepository.findAll(pageable);
    }

    @Override
    public List<ProductBattery> findAllProducts() {
        return productBatteryRepository.findAll();
    }

    @Override
    public Battery lend(EBike eBike, Battery battery) {
        battery.setShopId(null);
        battery.setEbikeSn(eBike.getSn());
        battery.setUpdateTime(LocalDateTime.now());
        return batteryRepository.save(battery);
    }

    @Override
    public Battery returnBattery(Staff staff, String batterySn) throws GException {
        Battery battery = batteryRepository.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }
        battery.setEbikeSn(null);
        battery.setShopId(staff.getShopId());
        battery.setUpdateTime(LocalDateTime.now());
        return batteryRepository.save(battery);
    }
}
