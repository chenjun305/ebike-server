package net.zriot.ebike.pojo.request.ebike;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class JoinMembershipParams {
    @NotNull
    private String ebikeSn;
    @NotNull
    private BigDecimal membership;
    @NotNull
    private BigDecimal monthFee;

    private String currency;
}