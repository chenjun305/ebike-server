package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/4/16.
 */
public enum StaffRole {
    UNKNOWN(0),
    SHOP_OWNER(1),
    SHOP_STAFF(2),
    OPERATE(3);

    private int role;

    StaffRole(int role) {
        this.role = role;
    }

    public int get() {
        return role;
    }

    public static StaffRole getRole(int role) {
        StaffRole[] roles = StaffRole.values();
        for (StaffRole r : roles) {
            if (r.get() == role) {
                return r;
            }
        }
        return null;
    }
}
