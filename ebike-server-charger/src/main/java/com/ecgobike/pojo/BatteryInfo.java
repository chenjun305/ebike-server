package com.ecgobike.pojo;

import lombok.Data;

/**
 * Created by ChenJun on 2018/9/18.
 */
@Data
public class BatteryInfo {
    private String batteryId;
    private String id;
    private String chargerName;
    private int status;
    private String batteryVolume;
}
