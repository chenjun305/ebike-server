package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/27.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PaymentOrderVO {
    private String sn;
    private Integer type;
    private BigDecimal price;
    private String currency;
    private String ebikeSn;
    private Long productId;
    private String uid;
    private String staffUid;
    private Long shopId;
    private LocalDate payDate;
    private Integer monthNum;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer status;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
