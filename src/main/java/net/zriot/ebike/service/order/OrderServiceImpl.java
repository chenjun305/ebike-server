package net.zriot.ebike.service.order;

import net.zriot.ebike.common.enums.OrderType;
import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.entity.order.UserOrder;
import net.zriot.ebike.pojo.request.order.Money;
import net.zriot.ebike.repository.order.UserOrderRepository;
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
    UserOrderRepository userOrderRepository;

    @Override
    public UserOrder createUserOrder(OrderType type, EBike eBike, Money fee) {
        UserOrder order = new UserOrder();
        order.setSn(IdGen.genOrderSn());
        order.setType(type.get());
        order.setAmount(fee.getAmount());
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
