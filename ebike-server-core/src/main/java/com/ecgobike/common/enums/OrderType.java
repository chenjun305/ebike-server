package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/3/16.
 * 订单类型
 * 1卖车
 * 2店员为用户充值
 * 3店员为用户加入会员
 * 4店员为用户更新包月
 * 5用户加入会员
 * 6用户更新包月
 */
public enum OrderType {
    UNKNOWN(0),
    SELL_EBIKE(1),
    STAFF_TOPUP_USER(2),
    STAFF_JOIN_MEMBERSHIP(3),
    STAFF_RENEW_MONTHLY(4),
    USER_JOIN_MEMBERSHIP(5),
    USER_RENEW_MONTHLY(6);

    private int type;

    OrderType(int type) {
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
