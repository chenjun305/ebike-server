package com.ecgobike.entity;

import com.ecgobike.common.enums.ProductType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/28.
 */
@Entity
@Table(name = "product")
@Data
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private ProductType type;
    private String name;
    private BigDecimal price;
    private String currency;
    private String iconUrl;
    private String model;
    private String color;
    private String desc;
    private Byte status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
