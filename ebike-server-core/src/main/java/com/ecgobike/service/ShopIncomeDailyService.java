package com.ecgobike.service;

import com.ecgobike.entity.ShopIncomeDaily;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ChenJun on 2018/5/4.
 */
public interface ShopIncomeDailyService {
    ShopIncomeDaily findOneByShopIdAndPayDate(Long shopId, LocalDate payDate);
    List<ShopIncomeDaily> saveAll(List<ShopIncomeDaily> list);
}
