package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class RenewParams {
    @NotNull
    private String ebikeSn;

    @NotNull
    private BigDecimal monthFee;

    private String currency;
}
