package com.ecgobike.service.impl;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.*;
import com.ecgobike.repository.EBikeRepository;
import com.ecgobike.repository.OrderMembershipRepository;
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

    @Autowired
    OrderMembershipRepository orderMembershipRepository;

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
    public OrderMembership joinMembership(EBike eBike) {
        eBike.setIsMembership((byte)1);
        eBike.setExpireDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        eBikeRepository.save(eBike);

        OrderMembership order = new OrderMembership();
        order.setSn(IdGen.genOrderSn());
        order.setType(OrderType.MEMBERSHIP_AND_MONTH_PAY.get());
        order.setPrice(Constants.MEMBERSHIP_FEE.add(Constants.MONTH_FEE));
        order.setCurrency(Constants.CURRENCY);
        order.setEbikeSn(eBike.getSn());
        order.setUid(eBike.getUid());
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(1));
        order.setStatus((byte)1);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return orderMembershipRepository.save(order);
    }

    @Override
    public OrderMembership renew(EBike eBike) {
        eBike.setExpireDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        eBikeRepository.save(eBike);

        OrderMembership order = new OrderMembership();
        order.setSn(IdGen.genOrderSn());
        order.setType(OrderType.MONTH_PAY.get());
        order.setPrice(Constants.MONTH_FEE);
        order.setCurrency(Constants.CURRENCY);
        order.setEbikeSn(eBike.getSn());
        order.setUid(eBike.getUid());
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(1));
        order.setStatus((byte)1);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return orderMembershipRepository.save(order);
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

    @Override
    public List<OrderSellEBike> findAllSall() {
        return orderSellEBikeRepository.findAll();
    }
}
