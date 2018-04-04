package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.Range;
import lombok.Data;

/**
 * Created by ChenJun on 2018/4/4.
 */
@Data
public class PurchaseParams extends AuthParams {
    @Range(Min = 1)
    private Long productId;

    @Range(Min = 1)
    private Integer requireNum;
}
