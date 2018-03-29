package com.ecgobike.pojo.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/15.
 */
@Data
@AllArgsConstructor
public class Money {
    private BigDecimal amount;
    private String currency;
}
