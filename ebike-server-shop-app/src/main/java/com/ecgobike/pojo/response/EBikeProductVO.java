package com.ecgobike.pojo.response;

import lombok.Data;

/**
 * Created by ChenJun on 2018/3/26.
 */
@Data
public class EBikeProductVO {
    private Long productId;
    private String model;
    private String color;
    private String iconUrl;
    private Integer sellNum;
    private Integer stockNum;
}
