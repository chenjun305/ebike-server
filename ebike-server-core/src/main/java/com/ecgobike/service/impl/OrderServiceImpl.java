package com.ecgobike.service.impl;

import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.OrderMembership;
import com.ecgobike.pojo.request.Money;
import com.ecgobike.repository.OrderMembershipRepository;
import com.ecgobike.repository.OrderTopupRepository;
import com.ecgobike.service.OrderService;
import com.ecgobike.entity.OrderTopup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/15.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderMembershipRepository userOrderRepository;

    @Autowired
    OrderTopupRepository orderTopupRepository;

    @Override
    public OrderMembership createUserOrder(OrderType type, EBike eBike, Money fee) {
        OrderMembership order = new OrderMembership();
        order.setSn(IdGen.genOrderSn());
        order.setType(type.get());
        order.setPrice(fee.getAmount());
        order.setCurrency(fee.getCurrency());
        order.setEbikeSn(eBike.getSn());
        order.setUid(eBike.getUid());
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(1));
        order.setStatus((byte)1);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return userOrderRepository.save(order);
    }

    @Override
    public OrderTopup createTopupOrder(String staffUid, String uid, Money money) {
        OrderTopup orderTopup = new OrderTopup();
        orderTopup.setSn(IdGen.genOrderSn());
        orderTopup.setType((byte)1);
        orderTopup.setStaffUid(staffUid);
        orderTopup.setUid(uid);
        orderTopup.setAmount(money.getAmount());
        orderTopup.setCurrency(money.getCurrency());
        orderTopup.setStatus((byte)1);

        return orderTopupRepository.save(orderTopup);
    }


}
