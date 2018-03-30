package com.ecgobike.service;

import com.ecgobike.common.enums.OrderType;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.Order;
import com.ecgobike.entity.Staff;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ChenJun on 2018/3/15.
 */
public interface OrderService {
    Order createTopupOrder(Staff staff, User user, Money money);
    Order createMembershipOrder(OrderType type, EBike eBike, User user, Staff staff);
    Order createSellOrder(Staff staff, User user, EBike eBike);
    Page<Order> findAllSall(Pageable pageable);
}
