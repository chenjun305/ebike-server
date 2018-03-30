package com.ecgobike.service.impl;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.*;
import com.ecgobike.repository.EBikeRepository;
import com.ecgobike.repository.OrderMembershipRepository;
import com.ecgobike.repository.OrderSellEBikeRepository;
import com.ecgobike.repository.ProductEBikeRepository;
import com.ecgobike.service.EBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<EBike> findAll(Pageable pageable) {
        return eBikeRepository.findAll(pageable);
    }

    @Override
    public List<EBike> findAllByUid(String uid) {
        return eBikeRepository.findAllByUid(uid);
    }

    @Override
    public Page<ProductEBike> findAllProducts(Pageable pageable) {
        return productEBikeRepository.findAll(pageable);
    }

    @Override
    public EBike findOneBySn(String sn) {
        return eBikeRepository.findOneBySn(sn);
    }

    @Override
    public OrderMembership joinMembership(String ebikeSn) throws GException {
        // check ebike
        EBike eBike = eBikeRepository.findOneBySn(ebikeSn);
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (eBike.getIsMembership() == 1) {
            throw new GException(ErrorConstants.ALREADY_MEMBERSHIP);
        }

        eBike.setIsMembership((byte)1);
        eBike.setExpireDate(LocalDate.now().plusMonths(1));
        eBike.setUpdateTime(LocalDateTime.now());
        eBikeRepository.save(eBike);
        return createMembershipOrder(OrderType.MEMBERSHIP_AND_MONTH_PAY, eBike);
    }

    @Override
    public OrderMembership renew(String ebikeSn) throws GException {
        // check ebike
        EBike eBike = eBikeRepository.findOneBySn(ebikeSn);
        if (eBike == null) {
            throw new GException(ErrorConstants.NOT_EXIST_EBIKE);
        }
        if (eBike.getIsMembership() == 0) {
            throw new GException(ErrorConstants.NO_MEMBERSHIP);
        }
        if (eBike.getExpireDate() != null && eBike.getExpireDate().isAfter(LocalDate.now())) {
            throw new GException(ErrorConstants.ALREADY_RENEW);
        }

        return createMembershipOrder(OrderType.MONTH_PAY, eBike);
    }

    protected OrderMembership createMembershipOrder(OrderType type, EBike eBike) {
        OrderMembership order = new OrderMembership();
        order.setSn(IdGen.genOrderSn());
        order.setType(type.get());
        if (type == OrderType.MEMBERSHIP_AND_MONTH_PAY) {
            order.setPrice(Constants.MEMBERSHIP_FEE.add(Constants.MONTH_FEE));
        } else {
            order.setPrice(Constants.MONTH_FEE);
        }
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
    public Page<OrderSellEBike> findAllSall(Pageable pageable) {
        return orderSellEBikeRepository.findAll(pageable);
    }
}
