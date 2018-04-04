package com.ecgobike.entity;

import com.ecgobike.common.enums.PurchaseOrderStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by ChenJun on 2018/4/4.
 */
@Entity
@Table(name = "purchase_order")
@Data
public class PurchaseOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "product_id")
//    private Product product;
    private Long productId;

    private Integer requireNum;
    private Integer permitNum;
    private String staffUid;
    private Long shopId;
    private String permitUid;
    private String departureUid;
    private String takeOverUid;

    private Date purchaseTime;
    private Date permitTime;
    private Date departureTime;
    private Date takeOverTime;

    private PurchaseOrderStatus status;

    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
