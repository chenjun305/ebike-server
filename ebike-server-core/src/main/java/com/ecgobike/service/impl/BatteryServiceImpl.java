package com.ecgobike.service.impl;

import com.ecgobike.common.enums.BatteryStatus;
import com.ecgobike.entity.*;
import com.ecgobike.service.BatteryService;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.exception.GException;
import com.ecgobike.repository.BatteryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
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
    public Page<Battery> findProductStockInShop(Product product, Long shopId, Pageable pageable) {
        Battery battery = new Battery();
        battery.setProduct(product);
        battery.setShopId(shopId);
        Example<Battery> example = Example.of(battery);
        return batteryRepository.findAll(example, pageable);
    }

    @Override
    public long countProductStockInShop(Product product, Long shopId) {
        Battery battery = new Battery();
        battery.setProduct(product);
        battery.setShopId(shopId);
        Example<Battery> example = Example.of(battery);
        return batteryRepository.count(example);
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
            battery.setStatus(BatteryStatus.IN_SHOP);
            battery.setCreateTime(LocalDateTime.now());
            battery.setUpdateTime(LocalDateTime.now());
            list.add(battery);
        }
        return batteryRepository.saveAll(list);
    }

    @Override
    public Battery canLend(String batterySn, String ebikeSn, Long staffShopId) throws GException {
        Battery battery = batteryRepository.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() != null) {
            throw new GException(ErrorConstants.NOT_RETURNED_BATTERY);
        }
        if (staffShopId != null && battery.getShopId() != null && battery.getShopId() != staffShopId) {
            throw new GException(ErrorConstants.NOT_YOUR_SHOP_BATTERY);
        }
        Battery battery2 = batteryRepository.findOneByEbikeSn(ebikeSn);
        if (battery2 != null) {
            throw new GException(ErrorConstants.NOT_RETURN_OLD_BATTERY);
        }
        return battery;
    }

    @Override
    public Battery lend(EBike eBike, Battery battery) {
        battery.setShopId(null);
        battery.setEbikeSn(eBike.getSn());
        battery.setStatus(BatteryStatus.LEND_OUT);
        battery.setUpdateTime(LocalDateTime.now());
        return batteryRepository.save(battery);
    }

    @Override
    public Battery returnBattery(Long staffShopId, String batterySn) throws GException {
        Battery battery = batteryRepository.findOneBySn(batterySn);
        if (battery == null) {
            throw new GException(ErrorConstants.NOT_EXIST_BATTERY);
        }
        if (battery.getEbikeSn() == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }
        battery.setEbikeSn(null);
        battery.setShopId(staffShopId);
        battery.setStatus(BatteryStatus.IN_SHOP);
        battery.setUpdateTime(LocalDateTime.now());
        return batteryRepository.save(battery);
    }
}
