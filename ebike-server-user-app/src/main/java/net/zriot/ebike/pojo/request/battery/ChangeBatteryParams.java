package net.zriot.ebike.pojo.request.battery;

import lombok.Data;
import net.zriot.ebike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/12.
 */
@Data
public class ChangeBatteryParams {
    @NotNull
    private String ebikeSn;
    @NotNull
    private String batterySn;
}
