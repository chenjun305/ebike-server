package com.ecgobike.pojo.request;

import com.ecgobike.common.annotation.NotNull;
import com.ecgobike.pojo.request.AuthParams;
import lombok.Data;

/**
 * Created by ChenJun on 2018/3/12.
 */
@Data
public class LendBatteryParams extends AuthParams {
    @NotNull
    private String ebikeSn;
    @NotNull
    private String batterySn;
}
