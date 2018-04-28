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
    Page<Battery> findProductStockInShop(Product product, Long shopId, Pageable pageable);
    long countProductStockInShop(Product product, Long shopId);
    List<Battery> shopIn(List<Logistics> logisticsList);
    Battery canLend(String batterySn, String ebikeSn, Long staffShopId) throws GException;
    Battery lend(EBike eBike, Battery battery);
    Battery returnBattery(Long staffShopId, String batterySn) throws GException;
}
