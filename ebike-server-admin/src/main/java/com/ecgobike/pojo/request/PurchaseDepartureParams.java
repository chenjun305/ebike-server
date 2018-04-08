package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import lombok.Data;

import java.util.List;

/**
 * Created by ChenJun on 2018/4/8.
 */
@Data
public class PurchaseDepartureParams {
    @NotNull
    private String purchaseSn;

    @NotNull
    private List<String> snList;
}
