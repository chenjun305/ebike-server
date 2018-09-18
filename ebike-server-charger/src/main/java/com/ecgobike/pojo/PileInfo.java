package com.ecgobike.pojo;

import lombok.Data;

/**
 * Created by ChenJun on 2018/9/18.
 */
@Data
public class PileInfo {
    private int num;
    private int status;
    private String batteryId;
    private int batteryVolume;
}
