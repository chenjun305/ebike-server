package com.ecgobike.repository;

import com.ecgobike.entity.ShopIncomeDaily;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;

/**
 * Created by ChenJun on 2018/5/4.
 */
@Repository
@Transactional
public interface ShopIncomeDailyRepository extends JpaRepository<ShopIncomeDaily, Long> {
    ShopIncomeDaily findOneByShopIdAndPayDate(Long shopId, LocalDate payDate);
}
