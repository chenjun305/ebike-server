package com.ecgobike.service.impl;

import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.Battery;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.LendBattery;
import com.ecgobike.entity.ShopStaff;
import com.ecgobike.repository.LendBatteryRepository;
import com.ecgobike.service.LendBatteryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/3.
 */
@Service
public class LendBatteryServiceImpl implements LendBatteryService {

    @Autowired
    LendBatteryRepository lendBatteryRepository;

    @Override
    public LendBattery lend(EBike eBike, Battery battery, String lendStaffUid) {
        LendBattery lendBattery = new LendBattery();
        lendBattery.setSn(IdGen.genOrderSn());
        lendBattery.setBatterySn(battery.getSn());
        lendBattery.setEbikeSn(eBike.getSn());
        lendBattery.setLendTime(LocalDateTime.now());
        lendBattery.setUid(eBike.getUid());
        if (lendStaffUid != null) {
            lendBattery.setLendStaffUid(lendStaffUid);
        }
        lendBattery.setLendShopId(battery.getShopId());
        lendBattery.setStatus((byte)0);
        lendBattery.setCreateTime(LocalDateTime.now());
        lendBattery.setUpdateTime(LocalDateTime.now());
        return lendBatteryRepository.save(lendBattery);
    }

    @Override
    public LendBattery returnBattery(ShopStaff staff, String batterySn) throws GException {
        LendBattery lendBattery = lendBatteryRepository.findOneByBatterySnAndStatus(batterySn, (byte)0);;
        if (lendBattery == null) {
            throw new GException(ErrorConstants.NOT_LEND_BATTERY);
        }
        lendBattery.setReturnTime(LocalDateTime.now());
        lendBattery.setReturnShopId(staff.getShop().getId());
        lendBattery.setReturnStaffUid(staff.getUid());
        lendBattery.setStatus((byte)1);
        lendBattery.setUpdateTime(LocalDateTime.now());
        return lendBatteryRepository.save(lendBattery);
    }

    @Override
    public Page<LendBattery> findAllLendHistory(Pageable pageable) {
        return lendBatteryRepository.findAll(pageable);
    }
}
