package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/5/18.
 */
public enum OsType {
    UNKNOWN(0),
    ANDROID(1),
    IOS(2);

    private int type;

    OsType(int type) {
        this.type = type;
    }

    public int get() {
        return type;
    }

    public static OsType getType(int type) {
        OsType[] types = OsType.values();
        for (OsType t : types) {
            if (t.get() == type) {
                return t;
            }
        }
        return null;
    }
}
