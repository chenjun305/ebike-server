package com.ecgobike.service.impl;

import com.ecgobike.entity.Shop;
import com.ecgobike.entity.Staff;
import com.ecgobike.repository.StaffRepository;
import com.ecgobike.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Service
public class StaffServiceImpl implements StaffService {

    @Autowired
    StaffRepository staffRepository;

    @Override
    public Staff findOneByUid(String uid) {
        return staffRepository.findOneByUid(uid);
    }

    @Override
    public Long getShopIdByUid(String uid) {
        Staff staff = staffRepository.findOneByUid(uid);
        return staff.getShop().getId();
    }

    @Override
    public Staff create(String uid, Shop shop, String staffNum) {
        Staff staff = new Staff();
        staff.setUid(uid);
        staff.setShop(shop);
        staff.setStaffNum(staffNum);
        staff.setStatus(1);
        staff.setCreateTime(LocalDateTime.now());
        staff.setUpdateTime(LocalDateTime.now());
        return staffRepository.save(staff);
    }

    @Override
    public Page<Staff> findAll(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }
}
