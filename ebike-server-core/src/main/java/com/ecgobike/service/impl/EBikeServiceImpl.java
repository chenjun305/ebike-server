package com.ecgobike.service.impl;

import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.*;
import com.ecgobike.repository.EBikeRepository;
import com.ecgobike.repository.OrderSellEBikeRepository;
import com.ecgobike.repository.ProductEBikeRepository;
import com.ecgobike.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Service
public class EBikeServiceImpl implements EBikeService {
    @Autowired
    EBikeRepository eBikeRepository;

    @Autowired
    ProductEBikeRepository productEBikeRepository;

    @Autowired
    OrderSellEBikeRepository orderSellEBikeRepository;

    @Override
    public List<EBike> findAll() {
        return eBikeRepository.findAll();
    }

    @Override
    public List<EBike> findAllByUid(String uid) {
        return eBikeRepository.findAllByUid(uid);
    }

    @Override
    public List<ProductEBike> findAllProducts() {
        return productEBikeRepository.findAll();
    }

    @Override
    public EBike findOneBySn(String sn) {
        return eBikeRepository.findOneBySn(sn);
    }

    @Override
    public EBike joinMembership(EBike eBike) {
        eBike.setIsMembership((byte)1);
        eBike.setExpireDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public EBike renew(EBike eBike) {
        eBike.setExpireDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public EBike save(EBike eBike) {
        eBike.setUpdateTime(LocalDateTime.now());
        return eBikeRepository.save(eBike);
    }

    @Override
    public OrderSellEBike sell(Staff staff, User user, EBike eBike) {
        OrderSellEBike order = new OrderSellEBike();
        order.setSn(IdGen.genOrderSn());
        order.setEbikeSn(eBike.getSn());
        order.setUid(user.getUid());
        order.setStaffUid(staff.getUid());
        order.setShopId(staff.getShopId());
        order.setPrice(new BigDecimal(100));
        order.setCurrency("USD");
        order.setStatus((byte)1);
        orderSellEBikeRepository.save(order);

        eBike.setUid(user.getUid());
        eBike.setStatus((byte)1);
        eBike.setUpdateTime(LocalDateTime.now());
        eBikeRepository.save(eBike);
        return order;
    }
}
