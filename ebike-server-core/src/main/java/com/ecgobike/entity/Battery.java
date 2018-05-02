package com.ecgobike.entity;

import com.ecgobike.common.enums.BatteryStatus;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/12.
 */
@Entity
@Table(name = "battery")
@Data
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Long shopId;
    private String ebikeSn;
    private Integer battery;
    private BatteryStatus status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
