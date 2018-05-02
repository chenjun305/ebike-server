package com.ecgobike.pojo.response;

import com.ecgobike.common.enums.LogisticsStatus;
import lombok.Data;

import java.time.LocalDate;

/**
 * Created by ChenJun on 2018/5/2.
 */
@Data
public class EBikeInfoVO {
    private String sn;
    private String uid;
    private Byte isMembership;
    private Integer monthLeft;
    private Integer monthTotal;
    private LocalDate expireDate;

    private LogisticsStatus logisticsStatus;

    private ProductVO product;

    private Boolean isExpire;
}
