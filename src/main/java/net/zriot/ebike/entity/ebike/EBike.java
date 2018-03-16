package net.zriot.ebike.entity.ebike;

import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
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
    private String name;
    private String note;
    private Long shopId;
    private String uid;
    private LocalDateTime buyTime;
    private Byte isMembership;
    private BigDecimal membership;
    private BigDecimal monthFee;
    private LocalDate monthStartDate;
    private LocalDate monthEndDate;
    private String batterySn;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
