package com.ecgobike.service;

import com.ecgobike.common.enums.OrderType;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.OrderMembership;
import com.ecgobike.pojo.request.Money;
import com.ecgobike.entity.OrderTopup;

/**
 * Created by ChenJun on 2018/3/15.
 */
public interface OrderService {
    OrderTopup createTopupOrder(String staffUid, String uid, Money money);
}
