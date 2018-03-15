package net.zriot.ebike.entity.order;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/15.
 */
@Entity
@Table(name = "order_membership")
@Data
public class OrderMembership {
    @Id
    @GeneratedValue
    private Long id;

    private String sn;
    private BigDecimal amount;
    private String currency;
    private String ebikeSn;
    private String uid;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
