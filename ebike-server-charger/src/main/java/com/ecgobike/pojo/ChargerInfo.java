package com.ecgobike.pojo;

import lombok.Data;

import java.util.List;

/**
 * Created by ChenJun on 2018/9/18.
 */
@Data
public class ChargerInfo {
    private String id;
    private String chargerName;
    private String warehouse;
    private double lng;
    private double lat;
    private String province;
    private String city;
    private String block;
    private String street;
    private int status;
    private int isOnline;
    private List<PileInfo> PileList;
}
