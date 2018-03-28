package net.zriot.ebike.service.impl;

import net.zriot.ebike.entity.ProductBattery;
import net.zriot.ebike.repository.ProductBatteryRepository;
import net.zriot.ebike.service.ProductBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/28.
 */
@Service
public class ProductBatteryServiceImpl implements ProductBatteryService {
    @Autowired
    ProductBatteryRepository productBatteryRepository;

    @Override
    public List<ProductBattery> findAll() {
        return productBatteryRepository.findAll();
    }
}
