package com.ecgobike.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by ChenJun on 2018/9/18.
 */
@Data
public class FullListInfo {
    private String warehouse;
    private int fullNum;
    private List<BatteryInfo> batteryList;
}
