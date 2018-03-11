package net.zriot.ebike.pojo.request.ebike;

import net.zriot.ebike.common.annotation.NotNull;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/11.
 */
public class RenewParams {
    @NotNull
    private String ebikeSn;

    @NotNull
    private BigDecimal monthFee;

    public String getEbikeSn() {
        return ebikeSn;
    }

    public void setEbikeSn(String ebikeSn) {
        this.ebikeSn = ebikeSn;
    }

    public BigDecimal getMonthFee() {
        return monthFee;
    }

    public void setMonthFee(BigDecimal monthFee) {
        this.monthFee = monthFee;
    }
}
