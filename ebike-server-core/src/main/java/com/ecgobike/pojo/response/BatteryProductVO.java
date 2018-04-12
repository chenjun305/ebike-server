package com.ecgobike.pojo.response;

import lombok.Data;

/**
 * Created by ChenJun on 2018/3/26.
 */
@Data
public class BatteryProductVO {
    private Long productId;
    private String type;
    private String iconUrl;
    private long stockNum;
}
