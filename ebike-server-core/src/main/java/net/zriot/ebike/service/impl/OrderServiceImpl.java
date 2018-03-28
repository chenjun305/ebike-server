package net.zriot.ebike.service.impl;

import net.zriot.ebike.common.enums.OrderType;
import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.entity.EBike;
import net.zriot.ebike.entity.OrderMembership;
import net.zriot.ebike.pojo.request.Money;
import net.zriot.ebike.repository.OrderMembershipRepository;
import net.zriot.ebike.service.OrderService;
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
}
