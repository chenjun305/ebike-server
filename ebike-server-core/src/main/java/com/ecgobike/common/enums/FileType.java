package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/4/18.
 */
public enum FileType {
    UNKNOWN(0),
    USER_AVATAR(1),
    USER_IDCARD(3);

    private int type;

    FileType(int type) {
        this.type = type;
    }

    public int get() {
        return type;
    }

    public static FileType getType(int type) {
        FileType[] types = FileType.values();
        for (FileType t : types) {
            if (t.get() == type) {
                return t;
            }
        }
        return null;
    }
}
