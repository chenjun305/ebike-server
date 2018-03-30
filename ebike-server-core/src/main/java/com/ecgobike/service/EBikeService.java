package com.ecgobike.service;

import com.ecgobike.entity.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface EBikeService {
    Page<EBike> findAll(Pageable pageable);
    List<EBike> findAllByUid(String uid);
    Page<ProductEBike> findAllProducts(Pageable pageable);
    EBike findOneBySn(String sn);
    OrderMembership joinMembership(EBike eBike);
    OrderMembership renew(EBike eBike);
    EBike save(EBike eBike);
    OrderSellEBike sell(Staff staff, User user, EBike eBike);
    Page<OrderSellEBike> findAllSall(Pageable pageable);
}
