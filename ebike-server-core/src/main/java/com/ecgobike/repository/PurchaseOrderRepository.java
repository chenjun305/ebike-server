package com.ecgobike.repository;

import com.ecgobike.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/4/4.
 */
@Repository
@Transactional
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
}
