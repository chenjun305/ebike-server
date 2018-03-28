package net.zriot.ebike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/28.
 */
@Entity
@Table(name = "product_battery")
@Data
public class ProductBattery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String iconUrl;
    private String model;
    private String color;
    private String desc;
    private Byte status;

    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
