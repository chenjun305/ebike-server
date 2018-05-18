package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/5/18.
 */
public enum AppType {
    UNKNOWN(0),
    USER_APP(1),
    STAFF_APP(2);

    private int type;

    AppType(int type) {
        this.type = type;
    }

    public int get() {
        return type;
    }

    public static AppType getType(int type) {
        AppType[] types = AppType.values();
        for (AppType t : types) {
            if (t.get() == type) {
                return t;
            }
        }
        return null;
    }
}
