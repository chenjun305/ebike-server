package net.zriot.ebike.pojo.request;

import net.zriot.ebike.common.annotation.NotNull;

/**
 * Created by ChenJun on 2018/3/10.
 */
public class AuthParams {
    @NotNull
    private String uid;
    @NotNull
    private String sign;
    @NotNull
    private String token;
    public String getUid() {
        return uid;
    }
    public void setUid(String uid) {
        this.uid = uid;
    }
    public String getSign() {
        return sign;
    }
    public void setSign(String sign) {
        this.sign = sign;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
}
