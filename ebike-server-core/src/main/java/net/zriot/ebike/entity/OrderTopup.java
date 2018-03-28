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
@Table(name = "order_topup")
@Data
public class OrderTopup {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private Byte type;
    private String uid;
    private String staffUid;
    private BigDecimal amount;
    private String currency;
    private Byte status;
    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
