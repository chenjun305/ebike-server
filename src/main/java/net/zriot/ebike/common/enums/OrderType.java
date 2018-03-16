package net.zriot.ebike.common.enums;

/**
 * Created by ChenJun on 2018/3/16.
 */
public enum OrderType {
    UNKNOWN(0),
    MEMBERSHIP_AND_MONTH_PAY(1),
    MONTH_PAY(2);

    private int type;

    private OrderType(int type) {
        this.type = type;
    }

    public int get() {
        return type;
    }

    public static OrderType getType(int type) {
        OrderType[] types = OrderType.values();
        for (OrderType t : types) {
            if (t.get() == type) {
                return t;
            }
        }
        return null;
    }
}
