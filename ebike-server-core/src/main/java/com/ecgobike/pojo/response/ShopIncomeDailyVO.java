package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/5/4.
 */
@Data
public class ShopIncomeDailyVO {
    private Long id;
    private ShopVO shop;
    private LocalDate payDate;
    private BigDecimal price;
    private String currency;
    private Integer status;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
