package com.ecgobike.repository;

import com.ecgobike.common.enums.OrderType;
import com.ecgobike.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/3/16.
 */
@Repository
@Transactional
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    @Query(value = "select type, sum(price) as money from payment_order where shop_id=?1 and pay_date=?2 group by type",
            nativeQuery = true)
    List<Map<OrderType, BigDecimal>> sumDailyShopIncomeGroupByType(Long shopId, LocalDate day);
}
