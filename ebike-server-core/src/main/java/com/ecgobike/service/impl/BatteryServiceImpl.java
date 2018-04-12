package com.ecgobike.service.impl;

import com.ecgobike.entity.*;
import com.ecgobike.service.BatteryService;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.exception.GException;
import com.ecgobike.repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public Page<Battery> findAll(Pageable pageable) {
        return batteryRepository.findAll(pageable);
    }

    @Override
    public List<Battery> shopIn(List<Logistics> logisticsList) {
        List<Battery> list = new ArrayList<>();
        for (Logistics logistics : logisticsList) {
            Battery battery = new Battery();
            battery.setSn(logistics.getSn());
            battery.setProduct(logistics.getProduct());
            battery.setShopId(logistics.getShopId());
            battery.setBattery(100);
            battery.setStatus((byte)1);
            battery.setCreateTime(LocalDateTime.now());
            battery.setUpdateTime(LocalDateTime.now());
            list.add(battery);
        }
        return batteryRepository.saveAll(list);
    }

    @Override
    public Battery canLend(String batterySn, Staff staff) throws GException {
        Battery battery = batteryRepository.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() != null) {
            throw new GException(ErrorConstants.NOT_RETURNED_BATTERY);
        }
        if (staff != null && battery.getShopId() != null && battery.getShopId() != staff.getShopId()) {
            throw new GException(ErrorConstants.NOT_YOUR_SHOP_BATTERY);
        }
        return battery;
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
