package net.zriot.ebike.pojo.request;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/29.
 */
@Data
public class ReturnBatteryParams extends AuthParams {
    @NotNull
    private String batterySn;
}
