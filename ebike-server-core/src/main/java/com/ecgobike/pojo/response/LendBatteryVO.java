package com.ecgobike.pojo.response;

import com.ecgobike.common.constant.Constants;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Created by ChenJun on 2018/4/27.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LendBatteryVO {
    private String sn;
    private String batterySn;
    private String ebikeSn;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime lendTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime returnTime;
    private String uid;
    private String lendStaffUid;
    private Long lendShopId;
    private String returnStaffUid;
    private Long returnShopId;
    private Byte status;

    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime createTime;
    @JsonFormat(pattern= Constants.JSON_FORMAT_PATTERN)
    private LocalDateTime updateTime;
}
