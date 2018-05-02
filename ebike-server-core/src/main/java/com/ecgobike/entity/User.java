package com.ecgobike.entity;

import com.ecgobike.common.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String idCardNum;
    private String idCardPics;
    private BigDecimal money;
    private String currency;
    private String address;
    private Byte status;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
