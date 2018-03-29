package com.ecgobike.repository;

import com.ecgobike.entity.OrderSellEBike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Repository
@Transactional
public interface OrderSellEBikeRepository extends JpaRepository<OrderSellEBike, Long> {
}
