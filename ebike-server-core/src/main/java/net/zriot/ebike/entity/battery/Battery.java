package net.zriot.ebike.entity.battery;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String name;
    private String note;
    private Long shopId;
    private String ebikeSn;
    private String uid;
    private Integer battery;
    private Byte status;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
