package net.zriot.ebike.service;

import net.zriot.ebike.common.enums.OrderType;
import net.zriot.ebike.entity.EBike;
import net.zriot.ebike.entity.OrderMembership;
import net.zriot.ebike.entity.OrderTopup;
import net.zriot.ebike.pojo.request.Money;

/**
 * Created by ChenJun on 2018/3/15.
 */
public interface OrderService {
    OrderMembership createUserOrder(OrderType type, EBike eBike, Money fee);
    OrderTopup createTopupOrder(String staffUid, String uid, Money money);
}
