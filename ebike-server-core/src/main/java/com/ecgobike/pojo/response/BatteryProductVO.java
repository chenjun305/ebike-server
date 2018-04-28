package com.ecgobike.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * Created by ChenJun on 2018/3/26.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BatteryProductVO {
    private Long id;
    private String model;
    private String color;
    private String iconUrl;
    private String desc;
    private long stockNum;
}
