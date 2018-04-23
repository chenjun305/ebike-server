package com.ecgobike.service.impl;

import com.ecgobike.entity.ShopStaff;
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
    public ShopStaff findOneByUid(String uid) {
        return shopStaffRepository.findOneByUid(uid);
    }

    @Override
    public Long getShopIdByUid(String uid) {
        ShopStaff shopStaff = shopStaffRepository.findOneByUid(uid);
        return shopStaff.getShopId();
    }

    @Override
    public ShopStaff create(String uid, Long shopId, String staffNum) {
        ShopStaff shopStaff = new ShopStaff();
        shopStaff.setUid(uid);
        shopStaff.setShopId(shopId);
        shopStaff.setStaffNum(staffNum);
        shopStaff.setStatus(1);
        shopStaff.setCreateTime(LocalDateTime.now());
        shopStaff.setUpdateTime(LocalDateTime.now());
        return shopStaffRepository.save(shopStaff);
    }

    @Override
    public Page<ShopStaff> findAll(Pageable pageable) {
        return shopStaffRepository.findAll(pageable);
    }
}
