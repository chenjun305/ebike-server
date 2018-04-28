package com.ecgobike.pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/3/26.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EBikeProductVO {
    private Long id;
    private String model;
    private String color;
    private String iconUrl;
    private BigDecimal price;
    private String currency;
    private String desc;
    private long sellNum;
    private long stockNum;
}
