package net.zriot.ebike.model.battery;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/12.
 */
@Entity
@Table(name = "battery")
@Data
public class Battery {
    @Id
    @GeneratedValue
    private Long id;

    private String sn;
    private String name;
    private String note;
    private Long shopId;
    private String ebikeSn;
    private String uid;
    private Integer battery;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
