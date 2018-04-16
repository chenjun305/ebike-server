package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.Range;
import lombok.Data;

/**
 * Created by ChenJun on 2018/4/8.
 */
@Data
public class PurchasePermitParams extends AuthParams {
    @NotNull
    private String purchaseSn;
    @Range(Min = 0)
    private Integer permitNum;
}
