package com.ecgobike.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/5/4.
 */
@Entity
@Table(name = "shop_income_daily")
@Data
public class ShopIncomeDaily implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long shopId;
    private LocalDate payDate;
    private BigDecimal price;
    private String currency;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
