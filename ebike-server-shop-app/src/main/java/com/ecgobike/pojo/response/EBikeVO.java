package com.ecgobike.pojo.response;

import lombok.Data;

/**
 * Created by ChenJun on 2018/3/26.
 */
@Data
public class EBikeVO {
    private String model;
    private String color;
    private String imgUrl;
    private Integer sellNum;
    private Integer stockNum;
}
