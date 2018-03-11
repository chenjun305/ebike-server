package net.zriot.ebike.common.enums;

/**
 * Created by ChenJun on 2018/3/11.
 */
public enum Gender {
    UNKNOWN((byte)0),
    MALE((byte)1),
    FEMALE((byte)2);

    private byte type;

    private Gender(byte type) {
        this.type = type;
    }

    public byte get() {
        return type;
    }

    public static Gender getType(byte type) {
        Gender[] types = Gender.values();
        for (Gender t : types) {
            if (t.get() == type) {
                return t;
            }
        }
        return null;
    }
}
