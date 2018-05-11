package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.enums.StaffRole;
import com.ecgobike.entity.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by ChenJun on 2018/4/26.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StaffInfoVO {
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

    private ShopVO shop;

    private String staffNum;

    List<StaffRole> roles;
}
