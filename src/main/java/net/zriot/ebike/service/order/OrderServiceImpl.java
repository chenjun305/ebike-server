package net.zriot.ebike.service.order;

import net.zriot.ebike.common.util.IdGen;
import net.zriot.ebike.entity.ebike.EBike;
import net.zriot.ebike.entity.order.OrderMembership;
import net.zriot.ebike.entity.order.OrderMonthPay;
import net.zriot.ebike.pojo.request.order.Money;
import net.zriot.ebike.repository.order.OrderMembershipRepository;
import net.zriot.ebike.repository.order.OrderMonthPayRepository;
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
    OrderMembershipRepository orderMembershipRepository;
    @Autowired
    OrderMonthPayRepository orderMonthPayRepository;

    @Override
    public OrderMembership joinMembership(EBike eBike, Money fee) {
        OrderMembership order = new OrderMembership();
        order.setSn(IdGen.genOrderSn());
        order.setAmount(fee.getAmount());
        order.setCurrency(fee.getCurrency());
        order.setEbikeSn(eBike.getSn());
        order.setUid(eBike.getUid());
        order.setStatus((byte)1);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return orderMembershipRepository.save(order);
    }

    @Override
    public OrderMonthPay renewMonthly(EBike eBike, Money fee) {
        OrderMonthPay order = new OrderMonthPay();
        order.setSn(IdGen.genOrderSn());
        order.setAmount(fee.getAmount());
        order.setCurrency(fee.getCurrency());
        order.setEbikeSn(eBike.getSn());
        order.setUid(eBike.getUid());
        order.setStartDate(LocalDate.now());
        order.setEndDate(LocalDate.now().plusMonths(1));
        order.setStatus((byte)1);
        order.setCreateTime(LocalDateTime.now());
        order.setUpdateTime(LocalDateTime.now());
        return orderMonthPayRepository.save(order);
    }
}
