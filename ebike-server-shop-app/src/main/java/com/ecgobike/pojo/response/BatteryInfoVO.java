package com.ecgobike.pojo.response;

import com.ecgobike.common.enums.BatteryStatus;
import com.ecgobike.entity.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by ChenJun on 2018/4/27.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatteryInfoVO {
    private String sn;
    private Long shopId;
    private String ebikeSn;
    private BatteryStatus status;
}
