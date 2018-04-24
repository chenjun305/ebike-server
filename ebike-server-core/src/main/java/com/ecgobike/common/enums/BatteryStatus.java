package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/4/24.
 */
public enum BatteryStatus {
    UNKNOWN(0),
    IN_SHOP(1),
    LEND_OUT(2);

    private int status;

    BatteryStatus(int status) {
        this.status = status;
    }

    public int get() {
        return status;
    }

    public static BatteryStatus getStatus(int status) {
        BatteryStatus[] statuses = BatteryStatus.values();
        for (BatteryStatus t : statuses) {
            if (t.get() == status) {
                return t;
            }
        }
        return null;
    }
}
