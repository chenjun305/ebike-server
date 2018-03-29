package com.ecgobike.common.enums;

/**
 * Created by ChenJun on 2018/3/10.
 */
public enum Auth {
    NULL(0),
    USER(1),
    STAFF(2);

    public int auth;

    private Auth(int auth) {
        this.auth = auth;
    }

    public int get() {
        return auth;
    }

    public static Auth get(int auth) {
        Auth[] purposes = Auth.values();
        for (Auth au : purposes) {
            if (au.get() == auth) {
                return au;
            }
        }
        return null;
    }
}
