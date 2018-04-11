package com.ecgobike.service.impl;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.constant.MonthNumFee;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.EBike;
import com.ecgobike.entity.PaymentOrder;
import com.ecgobike.entity.Staff;
import com.ecgobike.entity.User;
import com.ecgobike.pojo.request.Money;
import com.ecgobike.repository.PaymentOrderRepository;
import com.ecgobike.service.PaymentOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/15.
 */
@Service
public class PaymentOrderServiceImpl implements PaymentOrderService {

    @Autowired
    PaymentOrderRepository paymentOrderRepository;

    @Override
    public PaymentOrder createTopupOrder(Staff staff, User user, Money money) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setSn(IdGen.genOrderSn());
        paymentOrder.setType(OrderType.STAFF_TOPUP_USER.get());
        paymentOrder.setPrice(money.getAmount());
        paymentOrder.setCurrency(money.getCurrency());
        paymentOrder.setUid(user.getUid());
        paymentOrder.setStaffUid(staff.getUid());
        paymentOrder.setShopId(staff.getShopId());
        paymentOrder.setStatus(1);
        paymentOrder.setCreateTime(LocalDateTime.now());
        paymentOrder.setUpdateTime(LocalDateTime.now());
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder createMembershipOrder(OrderType type, EBike eBike, Staff staff, Integer monthNum) throws GException {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setSn(IdGen.genOrderSn());
        paymentOrder.setType(type.get());
        if (type == OrderType.USER_JOIN_MEMBERSHIP || type == OrderType.STAFF_JOIN_MEMBERSHIP) {
            paymentOrder.setPrice(Constants.MEMBERSHIP_FEE);
        } else if (type == OrderType.USER_RENEW_MONTHLY || type == OrderType.STAFF_RENEW_MONTHLY) {
            BigDecimal monthFee = MonthNumFee.getFee(monthNum);
            if (monthFee == null) {
                throw new GException(ErrorConstants.NOT_EXIST_MONTH_NUM_FEE_RULE);
            }
            paymentOrder.setPrice(monthFee);
            paymentOrder.setMonthNum(monthNum);
            paymentOrder.setStartDate(LocalDate.now());
            paymentOrder.setEndDate(LocalDate.now().plusMonths(1));
        }
        paymentOrder.setCurrency(Constants.CURRENCY);
        paymentOrder.setEbikeSn(eBike.getSn());
        paymentOrder.setUid(eBike.getUid());
        if (type == OrderType.STAFF_JOIN_MEMBERSHIP || type == OrderType.STAFF_RENEW_MONTHLY) {
            paymentOrder.setStaffUid(staff.getUid());
            paymentOrder.setShopId(staff.getShopId());
        }
        paymentOrder.setStatus(1);
        paymentOrder.setCreateTime(LocalDateTime.now());
        paymentOrder.setUpdateTime(LocalDateTime.now());
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder createSellOrder(Staff staff, User user, EBike eBike) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setSn(IdGen.genOrderSn());
        paymentOrder.setType(OrderType.SELL_EBIKE.get());

        paymentOrder.setPrice(eBike.getProduct().getPrice());
        paymentOrder.setCurrency(eBike.getProduct().getCurrency());

        paymentOrder.setEbikeSn(eBike.getSn());
        paymentOrder.setUid(user.getUid());
        paymentOrder.setStaffUid(staff.getUid());
        paymentOrder.setShopId(staff.getShopId());
        paymentOrder.setStatus(1);
        paymentOrder.setCreateTime(LocalDateTime.now());
        paymentOrder.setUpdateTime(LocalDateTime.now());
        return paymentOrderRepository.save(paymentOrder);
    }


    @Override
    public Page<PaymentOrder> findAllSall(Pageable pageable) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setType(OrderType.SELL_EBIKE.get());
        Example<PaymentOrder> example = Example.of(paymentOrder);

        return paymentOrderRepository.findAll(example, pageable);
    }
}
