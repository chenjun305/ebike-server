package com.ecgobike.service;

import com.ecgobike.common.enums.OrderType;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.Staff;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Created by ChenJun on 2018/3/15.
 */
public interface PaymentOrderService {
    PaymentOrder createTopupOrder(Staff staff, User user, Money money);
    PaymentOrder createMembershipOrder(OrderType type, EBike eBike, Staff staff);
    PaymentOrder createSellOrder(Staff staff, User user, EBike eBike);
    Page<PaymentOrder> findAllSall(Pageable pageable);
}