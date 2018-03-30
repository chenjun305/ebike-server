package com.ecgobike.pojo.request;

import lombok.Data;
import com.ecgobike.common.annotation.NotNull;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/11.
 */
@Data
public class JoinParams extends AuthParams {
    @NotNull
    private String ebikeSn;
}
