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
    private int status; // 状态0  无电池  1充电中  2充满
    private String batteryVolume; // 电池电量:-1代表无电池
}
