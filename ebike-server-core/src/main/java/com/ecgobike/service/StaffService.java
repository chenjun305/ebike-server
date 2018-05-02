package com.ecgobike.service;

import com.ecgobike.entity.Shop;
import com.ecgobike.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ChenJun on 2018/4/20.
 */
public interface StaffService {
    Staff findOneByUid(String uid);
    Shop getShopByUid(String uid);
    Long getShopIdByUid(String uid);
    Staff create(String uid, Shop shop, String staffNum);
    Page<Staff> findAll(Pageable pageable);
}
