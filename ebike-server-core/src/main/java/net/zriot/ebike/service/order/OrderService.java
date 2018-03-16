package net.zriot.ebike.service.order;

import net.zriot.ebike.common.enums.OrderType;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.entity.order.UserOrder;
import net.zriot.ebike.pojo.request.order.Money;

/**
 * Created by ChenJun on 2018/3/15.
 */
public interface OrderService {
    UserOrder createUserOrder(OrderType type, EBike eBike, Money fee);
}
