package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class RenewParams extends AuthParams {
    @NotNull
    private String ebikeSn;
}
