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
    OrderTopupRepository orderTopupRepository;

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
