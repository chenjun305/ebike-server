package net.zriot.ebike.entity.order;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/3/16.
 */
@Entity
@Table(name = "user_order")
@Data
public class UserOrder implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String sn;
    private Integer type;
    private BigDecimal amount;
    private String currency;
    private String ebikeSn;
    private String uid;
    private LocalDate startDate;
    private LocalDate endDate;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
