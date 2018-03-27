package net.zriot.ebike.pojo.request;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;
import net.zriot.ebike.common.annotation.Range;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Data
public class TopupParams {
    @NotNull
    private String tel;
    @Range(Min = 1)
    private BigDecimal amount;
    private String currency;
}
