package net.zriot.ebike.service.order;

import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.entity.order.OrderMembership;
import net.zriot.ebike.entity.order.OrderMonthPay;
import net.zriot.ebike.pojo.request.ebike.JoinMembershipParams;
import net.zriot.ebike.pojo.request.order.Money;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/15.
 */
public interface OrderService {
    OrderMembership joinMembership(EBike eBike, Money fee);
    OrderMonthPay renewMonthly(EBike eBike, Money fee);
}
