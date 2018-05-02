package com.ecgobike.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/20.
 */


@Entity
@Table(name = "staff")
@Data
public class Staff implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private String staffNum;
    private Integer status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;


}
