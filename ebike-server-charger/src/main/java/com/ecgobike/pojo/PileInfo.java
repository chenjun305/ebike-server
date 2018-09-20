package com.ecgobike.pojo;

import lombok.Data;

/**
 * Created by ChenJun on 2018/9/18.
 */
@Data
public class PileInfo {
    private int num; // 格口编号
    private int boxStatus; // 箱子状态 1 空闲 2 满箱
    private int chargerStatus; // 0：未充电 1:充电中 2:电池充满
    private String batteryId;
    private int batteryVolume; // 电池电量:-1代表无电池，

}
