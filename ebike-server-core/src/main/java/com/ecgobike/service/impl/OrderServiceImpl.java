package com.ecgobike.service.impl;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.Order;
import com.ecgobike.entity.Staff;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.Money;
import com.ecgobike.repository.OrderRepository;
import com.ecgobike.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Created by ChenJun on 2018/3/15.
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Override
    public Order createTopupOrder(Staff staff, User user, Money money) {
        Order order = new Order();
        order.setSn(IdGen.genOrderSn());
        order.setType(OrderType.STAFF_TOPUP_USER.get());
        order.setPrice(money.getAmount());
        order.setCurrency(money.getCurrency());
        order.setUid(user.getUid());
        order.setStaffUid(staff.getUid());
        order.setShopId(staff.getShopId());
        order.setStatus(1);
        return orderRepository.save(order);
    }

    @Override
    public Order createMembershipOrder(OrderType type, EBike eBike, User user, Staff staff) {
        Order order = new Order();
        order.setSn(IdGen.genOrderSn());
        order.setType(type.get());
        if (type == OrderType.USER_JOIN_MEMBERSHIP || type == OrderType.STAFF_JOIN_MEMBERSHIP) {
            order.setPrice(Constants.MEMBERSHIP_FEE.add(Constants.MONTH_FEE));
        } else if (type == OrderType.USER_RENEW_MONTHLY || type == OrderType.STAFF_RENEW_MONTHLY) {
            order.setPrice(Constants.MONTH_FEE);
        }
        order.setCurrency(Constants.CURRENCY);
        order.setEbikeSn(eBike.getSn());
        order.setUid(user.getUid());
        if (type == OrderType.STAFF_JOIN_MEMBERSHIP || type == OrderType.STAFF_RENEW_MONTHLY) {
            order.setStaffUid(staff.getUid());
            order.setShopId(staff.getShopId());
        }
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(1));
        order.setStatus(1);
        return orderRepository.save(order);
    }

    @Override
    public Order createSellOrder(Staff staff, User user, EBike eBike) {
        Order order = new Order();
        order.setSn(IdGen.genOrderSn());
        order.setType(OrderType.SELL_EBIKE.get());
        // TODO
        order.setPrice(new BigDecimal(100));
        order.setCurrency("USD");

        order.setEbikeSn(eBike.getSn());
        order.setUid(user.getUid());
        order.setStaffUid(staff.getUid());
        order.setShopId(staff.getShopId());
        order.setStatus(1);
        return orderRepository.save(order);
    }


    @Override
    public Page<Order> findAllSall(Pageable pageable) {
        Order order = new Order();
        order.setType(OrderType.SELL_EBIKE.get());
        Example<Order> example = Example.of(order);

        return orderRepository.findAll(example, pageable);
    }
}
