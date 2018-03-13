package net.zriot.ebike.model.user;

import lombok.Data;
import net.zriot.ebike.common.enums.Gender;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "user")
@Data
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String uid;
    private String tel;
    private String avatar;
    private String nickname;
    private Byte isReal;
    private String realName;
    private Byte gender;
    private BigDecimal money;
    private String currency;
    private String address;
    private Byte status;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
