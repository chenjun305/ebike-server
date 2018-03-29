package com.ecgobike.repository;

import com.ecgobike.entity.OrderTopup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/29.
 */
@Repository
@Transactional
public interface OrderTopupRepository extends JpaRepository<OrderTopup, Long> {
}
