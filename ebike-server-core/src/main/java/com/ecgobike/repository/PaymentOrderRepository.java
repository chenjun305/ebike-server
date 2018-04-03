package com.ecgobike.repository;

import com.ecgobike.entity.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/16.
 */
@Repository
@Transactional
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
}
