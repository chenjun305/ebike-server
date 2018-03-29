package com.ecgobike.service;

import com.ecgobike.entity.*;

import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
public interface EBikeService {
    List<EBike> findAll();
    List<EBike> findAllByUid(String uid);
    List<ProductEBike> findAllProducts();
    EBike findOneBySn(String sn);
    OrderMembership joinMembership(EBike eBike);
    OrderMembership renew(EBike eBike);
    EBike save(EBike eBike);
    OrderSellEBike sell(Staff staff, User user, EBike eBike);
}
