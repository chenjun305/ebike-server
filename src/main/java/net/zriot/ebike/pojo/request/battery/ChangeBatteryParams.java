package net.zriot.ebike.pojo.request.battery;

import net.zriot.ebike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/12.
 */
public class ChangeBatteryParams {
    @NotNull
    private String ebikeSn;
    @NotNull
    private String batterySn;

    public String getEbikeSn() {
        return ebikeSn;
    }

    public void setEbikeSn(String ebikeSn) {
        this.ebikeSn = ebikeSn;
    }

    public String getBatterySn() {
        return batterySn;
    }

    public void setBatterySn(String batterySn) {
        this.batterySn = batterySn;
    }
}
