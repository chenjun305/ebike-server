package com.ecgobike.service;

import com.ecgobike.entity.ShopStaff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ChenJun on 2018/4/20.
 */
public interface ShopStaffService {
    ShopStaff findOneByUid(String uid);
    Long getShopIdByUid(String uid);
    ShopStaff create(String uid, Long shopId, String staffNum);
    Page<ShopStaff> findAll(Pageable pageable);
}
