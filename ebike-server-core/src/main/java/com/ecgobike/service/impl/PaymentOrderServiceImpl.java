package com.ecgobike.service.impl;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.constant.ErrorConstants;
import com.ecgobike.common.constant.MonthNumFee;
import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.common.util.IdGen;
import com.ecgobike.entity.*;
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
import java.util.List;
import java.util.Map;

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
        paymentOrder.setType(OrderType.STAFF_TOPUP_USER);
        paymentOrder.setPrice(money.getAmount());
        paymentOrder.setCurrency(money.getCurrency());
        paymentOrder.setUid(user.getUid());
        paymentOrder.setTel(user.getTel());
        paymentOrder.setStaffUid(staff.getUid());
        paymentOrder.setShopId(staff.getShop().getId());
        paymentOrder.setPayDate(LocalDate.now());
        paymentOrder.setStatus(1);
        paymentOrder.setCreateTime(LocalDateTime.now());
        paymentOrder.setUpdateTime(LocalDateTime.now());
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder createMembershipOrder(OrderType type, EBike eBike, Staff staff, Integer monthNum) throws GException {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setSn(IdGen.genOrderSn());
        paymentOrder.setType(type);
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
        paymentOrder.setProduct(eBike.getProduct());
        paymentOrder.setUid(eBike.getUid());
        if (type == OrderType.STAFF_JOIN_MEMBERSHIP || type == OrderType.STAFF_RENEW_MONTHLY) {
            paymentOrder.setStaffUid(staff.getUid());
            paymentOrder.setShopId(staff.getShop().getId());
        }
        paymentOrder.setPayDate(LocalDate.now());
        paymentOrder.setStatus(1);
        paymentOrder.setCreateTime(LocalDateTime.now());
        paymentOrder.setUpdateTime(LocalDateTime.now());
        return paymentOrderRepository.save(paymentOrder);
    }

    @Override
    public PaymentOrder createSellOrder(Staff staff, User user, EBike eBike) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setSn(IdGen.genOrderSn());
        paymentOrder.setType(OrderType.SELL_EBIKE);
        paymentOrder.setPrice(eBike.getProduct().getPrice());
        paymentOrder.setCurrency(eBike.getProduct().getCurrency());
        paymentOrder.setEbikeSn(eBike.getSn());
        paymentOrder.setProduct(eBike.getProduct());
        paymentOrder.setUid(user.getUid());
        paymentOrder.setTel(user.getTel());
        paymentOrder.setStaffUid(staff.getUid());
        paymentOrder.setShopId(staff.getShop().getId());
        paymentOrder.setPayDate(LocalDate.now());
        paymentOrder.setStatus(1);
        paymentOrder.setCreateTime(LocalDateTime.now());
        paymentOrder.setUpdateTime(LocalDateTime.now());
        return paymentOrderRepository.save(paymentOrder);
    }


    @Override
    public Page<PaymentOrder> findAllSall(Pageable pageable) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setType(OrderType.SELL_EBIKE);
        Example<PaymentOrder> example = Example.of(paymentOrder);

        return paymentOrderRepository.findAll(example, pageable);
    }

    @Override
    public Page<PaymentOrder> findProductSellOrdersInShop(Product product, Long shopId, Pageable pageable) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setProduct(product);
        paymentOrder.setType(OrderType.SELL_EBIKE);
        paymentOrder.setShopId(shopId);

        Example<PaymentOrder> example = Example.of(paymentOrder);

        return paymentOrderRepository.findAll(example, pageable);
    }

    @Override
    public Page<PaymentOrder> findAllInShop(Long shopId, Pageable pageable) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setShopId(shopId);
        Example<PaymentOrder> example = Example.of(paymentOrder);

        return paymentOrderRepository.findAll(example, pageable);
    }

    @Override
    public Page<PaymentOrder> findAllShopIncome(Pageable pageable) {
        return paymentOrderRepository.findAll(pageable);
    }

    @Override
    public long countProductSellOrdersInShop(Product product, Long shopId) {
        return countSellOrdersBy(product, shopId);
    }

    @Override
    public long countProductSellOrders(Product product) {
        return countSellOrdersBy(product, null);
    }

    protected long countSellOrdersBy(Product product, Long shopId) {
        PaymentOrder paymentOrder = new PaymentOrder();
        paymentOrder.setType(OrderType.SELL_EBIKE);
        if (product != null) {
            paymentOrder.setProduct(product);
        }
        if (shopId != null) {
            paymentOrder.setShopId(shopId);
        }
        Example<PaymentOrder> example = Example.of(paymentOrder);

        return paymentOrderRepository.count(example);
    }

    @Override
    public List<Map> sumDailyShopIncomeGroupByType(Long shopId, LocalDate day) {
        return paymentOrderRepository.sumDailyShopIncomeGroupByType(shopId, day);
    }

    @Override
    public List<Map> sumDailyIncomeGroupByShop(LocalDate day) {
        return paymentOrderRepository.sumDailyIncomeGroupByShop(day);
    }
}
