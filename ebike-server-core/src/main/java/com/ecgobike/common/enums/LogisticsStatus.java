package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/4/10.
 */
public enum LogisticsStatus {
    UNKNOWN(0),
    STORAGE(1),
    TRANSIT(2),
    SHOP(3),
    SELLED(4);

    private int status;

    LogisticsStatus(int status) {
        this.status = status;
    }

    public int get() {
        return status;
    }

    public static LogisticsStatus getStatus(int status) {
        LogisticsStatus[] statuses = LogisticsStatus.values();
        for (LogisticsStatus t : statuses) {
            if (t.get() == status) {
                return t;
            }
        }
        return null;
    }
}
