package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/5/8.
 */
public enum SmsType {
    UNKNOWN(0),
    USER_LOGIN(1),
    STAFF_LOGIN(2),
    ADMIN_LOGIN(3),
    SELL_EBIKE(4);

    private int type;

    SmsType(int type) {
        this.type = type;
    }

    public int get() {
        return type;
    }

    public static SmsType getType(int type) {
        SmsType[] types = SmsType.values();
        for (SmsType t : types) {
            if (t.get() == type) {
                return t;
            }
        }
        return null;
    }
}
