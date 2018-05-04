package com.ecgobike.task;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.entity.ShopIncomeDaily;
import com.ecgobike.service.PaymentOrderService;
import com.ecgobike.service.ShopIncomeDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ChenJun on 2018/5/4.
 */
@Component
public class IncomeStatisticsTask {

    @Autowired
    PaymentOrderService paymentOrderService;

    @Autowired
    ShopIncomeDailyService shopIncomeDailyService;

    // 每小时的55分统计一次
    //@Scheduled(cron = "0 55 * * * ?")
    @Scheduled(cron = "0 * * * * ?")
    public void shopIncomeDailyStatistics() {
        LocalDate today = LocalDate.now();
        List<Map> list = paymentOrderService.sumDailyIncomeGroupByShop(today);
        List<ShopIncomeDaily> saveList = new ArrayList<>();
        for (Map map : list) {
            System.out.println("shopId = " + map.get("shop_id") + " , money = " + map.get("money"));
            Long shopId = Long.valueOf("" + map.get("shop_id"));
            BigDecimal money = (BigDecimal)map.get("money");
            ShopIncomeDaily shopIncomeDaily = shopIncomeDailyService.findOneByShopIdAndPayDate(shopId, today);
            if (shopIncomeDaily == null) {
                shopIncomeDaily = new ShopIncomeDaily();
                shopIncomeDaily.setShopId(shopId);
                shopIncomeDaily.setPayDate(today);
                shopIncomeDaily.setCurrency(Constants.CURRENCY);
                shopIncomeDaily.setStatus(1);
            }
            shopIncomeDaily.setPrice(money);
            shopIncomeDaily.setUpdateTime(LocalDateTime.now());
            saveList.add(shopIncomeDaily);
        }
        if (saveList.size() > 0) {
            shopIncomeDailyService.saveAll(saveList);
        }
    }

}
