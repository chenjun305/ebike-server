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
    Battery lend(EBike eBike, Battery battery);
    Battery returnBattery(Staff staff, String batterySn) throws GException;
}
