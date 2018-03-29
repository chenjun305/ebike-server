package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.Range;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Data
public class TopupParams extends AuthParams {
    @NotNull
    private String tel;
    @Range(Min = 1)
    private BigDecimal amount;
    private String currency;
}
