package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/5/9.
 */
public enum BookBatteryStatus {
    EXPIRED(0),
    NORMAL(1),
    LENDED(2),
    CANCELED(3);

    private int status;

    BookBatteryStatus(int status) {
        this.status = status;
    }

    public int get() {
        return status;
    }

    public static BookBatteryStatus getStatus(int status) {
        BookBatteryStatus[] statuses = BookBatteryStatus.values();
        for (BookBatteryStatus t : statuses) {
            if (t.get() == status) {
                return t;
            }
        }
        return null;
    }
}
