package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.ecgobike.common.enums.BookBatteryStatus;
import com.ecgobike.entity.Shop;
import com.ecgobike.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/5/9.
 */
@Data
public class BookBatteryVO {
    private Long id;

    private UserVO user;

    private String ebikeSn;

    private ShopVO shop;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime bookTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime expireTime;

    private String batterySn;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime lendTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime cancelTime;

    private BookBatteryStatus status;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
