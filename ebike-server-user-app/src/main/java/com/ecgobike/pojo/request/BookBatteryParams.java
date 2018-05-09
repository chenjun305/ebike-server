package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.common.annotation.Range;
import lombok.Data;

/**
 * Created by ChenJun on 2018/5/9.
 */
@Data
public class BookBatteryParams extends AuthParams {
    @NotNull
    private String ebikeSn;
    @Range(Min = 1)
    private Long shopId;
}
