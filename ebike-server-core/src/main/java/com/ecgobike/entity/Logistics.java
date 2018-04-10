package com.ecgobike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/10.
 */
@Entity
@Table(name = "logistics")
@Data
public class Logistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sn;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private LocalDateTime storageInTime;
    private LocalDateTime storageOutTime;
    private String purchaseSn;
    private Long shopId;
    private LocalDateTime shopInTime;
    private LocalDateTime shopOutTime;
    private Integer status;

    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
