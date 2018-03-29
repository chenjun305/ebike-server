package com.ecgobike.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/27.
 */
@Entity
@Table(name = "lend_battery")
@Data
public class LendBattery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private String batterySn;
    private String ebikeSn;
    private LocalDateTime lendTime;
    private LocalDateTime returnTime;
    private String uid;
    private String lendStaffUid;
    private Long lendShopId;
    private String returnStaffUid;
    private Long returnShopId;
    private Byte status;

    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
