package com.ecgobike.service.impl;

import com.ecgobike.entity.Shop;
import com.ecgobike.entity.Staff;
import com.ecgobike.repository.ShopStaffRepository;
import com.ecgobike.service.ShopStaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/20.
 */
@Service
public class ShopStaffServiceImpl implements ShopStaffService {

    @Autowired
    ShopStaffRepository shopStaffRepository;

    @Override
    public Staff findOneByUid(String uid) {
        return shopStaffRepository.findOneByUid(uid);
    }

    @Override
    public Long getShopIdByUid(String uid) {
        Staff staff = shopStaffRepository.findOneByUid(uid);
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
        return shopStaffRepository.save(staff);
    }

    @Override
    public Page<Staff> findAll(Pageable pageable) {
        return shopStaffRepository.findAll(pageable);
    }
}
