package com.ecgobike.pojo.response;

import com.ecgobike.entity.Product;
import lombok.Data;

import java.time.LocalDate;

/**
 * Created by ChenJun on 2018/4/25.
 */
@Data
public class EBikeInfoVO {
    private String sn;
    //private Product product;
    private String uid;
    private Byte isMembership;
    private Integer monthLeft;
    private Integer monthTotal;
    private LocalDate expireDate;
    private Byte status;
}
