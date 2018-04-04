package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/4/4.
 */
public enum PurchaseOrderStatus {
    UNKNOWN(0),
    REQUIRE(1),
    PERMIT(2),
    TRANSIT(3),
    TAKE_OVER(4);

    private int status;

    PurchaseOrderStatus(int status) {
        this.status = status;
    }

    public int get() {
        return status;
    }

    public static PurchaseOrderStatus getStatus(int status) {
        PurchaseOrderStatus[] statuses = PurchaseOrderStatus.values();
        for (PurchaseOrderStatus t : statuses) {
            if (t.get() == status) {
                return t;
            }
        }
        return null;
    }
}
