package com.ecgobike.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "ebike")
@Data
public class EBike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private String uid;
    private Byte isMembership;
    private Integer monthLeft;
    private Integer monthTotal;
    private LocalDate expireDate;
    private Byte status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
