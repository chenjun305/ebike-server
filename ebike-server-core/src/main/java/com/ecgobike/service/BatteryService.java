package com.ecgobike.service;

import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/12.
 */
public interface BatteryService {
    Battery findOneBySn(String sn);
    Page<Battery> findAll(Pageable pageable);
    Page<ProductBattery> findAllProducts(Pageable pageable);
    List<ProductBattery> findAllProducts();
    LendBattery lend(EBike eBike, Battery battery);
    LendBattery returnBattery(Staff staff, Battery battery) throws GException;
    Page<LendBattery> findAllLendHistory(Pageable pageable);
}
