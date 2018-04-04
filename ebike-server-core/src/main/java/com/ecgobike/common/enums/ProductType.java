package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/4/4.
 */
public enum ProductType {
    UNKNOWN(0),
    EBIKE(1),
    BATTERY(2);


    private int type;

    ProductType(int type) {
        this.type = type;
    }

    public int get() {
        return type;
    }

    public static ProductType getType(int type) {
        ProductType[] types = ProductType.values();
        for (ProductType t : types) {
            if (t.get() == type) {
                return t;
            }
        }
        return null;
    }
}
