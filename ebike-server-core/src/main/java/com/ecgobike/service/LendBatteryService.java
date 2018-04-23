package com.ecgobike.service;

import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.Battery;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.LendBattery;
import com.ecgobike.entity.ShopStaff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ChenJun on 2018/4/3.
 */
public interface LendBatteryService {
    LendBattery lend(EBike eBike, Battery battery, String lendStaffUid);
    LendBattery returnBattery(ShopStaff staff, String batterySn) throws GException;
    Page<LendBattery> findAllLendHistory(Pageable pageable);
}
