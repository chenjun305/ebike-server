package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/5/9.
 */
@Data
public class UserVO {
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
