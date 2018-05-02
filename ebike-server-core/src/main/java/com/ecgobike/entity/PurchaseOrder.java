package com.ecgobike.entity;

import com.ecgobike.common.enums.PurchaseOrderStatus;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/4.
 */
@Entity
@Table(name = "purchase_order")
@Data
public class PurchaseOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer requireNum;
    private Integer permitNum;
    private String staffUid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String permitUid;
    private String departureUid;
    private String takeOverUid;

    private LocalDateTime purchaseTime;
    private LocalDateTime permitTime;
    private LocalDateTime departureTime;
    private LocalDateTime takeOverTime;

    private PurchaseOrderStatus status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
