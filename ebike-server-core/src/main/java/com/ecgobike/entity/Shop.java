package com.ecgobike.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "shop")
@Data
public class Shop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String tel;
    private String address;
    private String openTime;
    private String description;
    private String latitude;
    private String longitude;
    private String geohash;
    private Integer batteryAvailable;
    private Byte status;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
