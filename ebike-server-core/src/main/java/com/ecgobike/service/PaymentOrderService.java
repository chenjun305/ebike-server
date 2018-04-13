package com.ecgobike.service;

import com.ecgobike.common.enums.OrderType;
import com.ecgobike.common.exception.GException;
import com.ecgobike.entity.*;
import com.ecgobike.pojo.request.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/15.
 */
public interface PaymentOrderService {
    PaymentOrder createTopupOrder(Staff staff, User user, Money money);
    PaymentOrder createMembershipOrder(OrderType type, EBike eBike, Staff staff, Integer monthNum) throws GException;
    PaymentOrder createSellOrder(Staff staff, User user, EBike eBike);
    Page<PaymentOrder> findAllSall(Pageable pageable);
    Page<PaymentOrder> findProductSellOrdersInShop(Product product, Long shopId, Pageable pageable);
    Page<PaymentOrder> findAllInShop(Long shopId, Pageable pageable);
    Page<PaymentOrder> findAllShopIncome(Pageable pageable);
    long countProductSellOrdersInShop(Product product, Long shopId);
    List<Map<OrderType, BigDecimal>> sumDailyShopIncomeGroupByType(Long shopId, LocalDate day);
}
