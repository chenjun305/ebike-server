package com.ecgobike.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/16.
 */
@Entity
@Table(name = "payment_order")
@Data
public class PaymentOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private Integer type;
    private BigDecimal price;
    private String currency;
    private String ebikeSn;
    private Long productId;
    private String uid;
    private String staffUid;
    private Long shopId;
    private Integer monthNum;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
