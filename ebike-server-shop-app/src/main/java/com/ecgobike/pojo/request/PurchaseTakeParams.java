package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import lombok.Data;

/**
 * Created by ChenJun on 2018/4/11.
 */
@Data
public class PurchaseTakeParams extends AuthParams {
    @NotNull
    private String purchaseSn;
}
