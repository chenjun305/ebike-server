package com.ecgobike.pojo.response;

import com.ecgobike.common.enums.ProductType;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Created by ChenJun on 2018/4/28.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVO {
    private Long id;

    private ProductType type;
    private String name;
    private BigDecimal price;
    private String currency;
    private String iconUrl;
    private String model;
    private String color;
    private String desc;
    private Byte status;
}
