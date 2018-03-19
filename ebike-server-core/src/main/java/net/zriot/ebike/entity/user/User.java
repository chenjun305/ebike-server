package net.zriot.ebike.entity.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
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

    @JsonIgnore
    private LocalDateTime createTime;
    @JsonIgnore
    private LocalDateTime updateTime;
}
