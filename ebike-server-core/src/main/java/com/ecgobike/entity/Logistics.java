package com.ecgobike.entity;

import com.ecgobike.common.enums.LogisticsStatus;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/10.
 */
@Entity
@Table(name = "logistics")
@Data
public class Logistics implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private LocalDateTime storageInTime;
    private LocalDateTime storageOutTime;
    private String purchaseSn;
    private LocalDateTime shopInTime;
    private LocalDateTime shopOutTime;
    private LogisticsStatus status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
