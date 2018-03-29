package com.ecgobike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Entity
@Table(name = "order_sell_ebike")
@Data
public class OrderSellEBike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private String ebikeSn;
    private String uid;
    private String staffUid;
    private BigDecimal price;
    private String currency;
    private Long shopId;
    private Byte status;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
