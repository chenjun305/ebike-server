package com.ecgobike.pojo.request;

import lombok.Data;
import com.ecgobike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/29.
 */
@Data
public class ReturnBatteryParams extends AuthParams {
    @NotNull
    private String batterySn;
}
