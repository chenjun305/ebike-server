package com.ecgobike.service.impl;

import com.ecgobike.entity.ShopIncomeDaily;
import com.ecgobike.repository.ShopIncomeDailyRepository;
import com.ecgobike.service.ShopIncomeDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by ChenJun on 2018/5/4.
 */
@Service
public class ShopIncomeDailyServiceImpl implements ShopIncomeDailyService {
    @Autowired
    ShopIncomeDailyRepository shopIncomeDailyRepository;

    @Override
    public ShopIncomeDaily findOneByShopIdAndPayDate(Long shopId, LocalDate payDate) {
        return shopIncomeDailyRepository.findOneByShopIdAndPayDate(shopId, payDate);
    }

    @Override
    public List<ShopIncomeDaily> saveAll(List<ShopIncomeDaily> list) {
        return shopIncomeDailyRepository.saveAll(list);
    }
}
