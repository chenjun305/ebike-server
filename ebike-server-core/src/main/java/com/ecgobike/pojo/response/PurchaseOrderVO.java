package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.enums.PurchaseOrderStatus;
import com.ecgobike.entity.Product;
import com.ecgobike.entity.Shop;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/26.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PurchaseOrderVO {
    private Long id;

    private String sn;

    private ProductVO product;
    private ShopVO shop;

    private Integer requireNum;
    private Integer permitNum;
    private String staffUid;

    private String permitUid;
    private String departureUid;
    private String takeOverUid;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime purchaseTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime permitTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime departureTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime takeOverTime;

    private PurchaseOrderStatus status;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    // @JsonIgnore
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
