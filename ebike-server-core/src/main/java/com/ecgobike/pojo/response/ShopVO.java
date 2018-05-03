package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/28.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ShopVO {
    private Long id;

    private String name;
    private String tel;
    private String address;
    private String openTime;
    private String description;
    private String latitude;
    private String longitude;
    private String geohash;
    private Byte status;

    private Long batteryAvailable;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
