package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.enums.LogisticsStatus;
import com.ecgobike.entity.Product;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/26.
 */
@Data
public class LogisticsVO {
    private Long id;
    private String sn;

    //private String productId;
    private ProductVO product;
    //private Long shopId;
    private ShopVO shop;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime storageInTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime storageOutTime;
    private String purchaseSn;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime shopInTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime shopOutTime;
    private LogisticsStatus status;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
