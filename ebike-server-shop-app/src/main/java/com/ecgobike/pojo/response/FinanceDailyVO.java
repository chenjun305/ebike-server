package com.ecgobike.pojo.response;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/4/13.
 */
@Data
public class FinanceDailyVO {
    private BigDecimal totalMoney;
    private BigDecimal sellEBikeMoney;
    private BigDecimal helpTopupMoney;
    private BigDecimal helpJoinMembershipMoney;
    private BigDecimal helpRenewMonthlyMoney;
}
