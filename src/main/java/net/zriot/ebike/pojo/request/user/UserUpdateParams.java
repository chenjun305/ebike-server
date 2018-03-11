package net.zriot.ebike.pojo.request.user;

import net.zriot.ebike.common.enums.Gender;

/**
 * Created by ChenJun on 2018/3/11.
 */
public class UserUpdateParams {
    private String nickname;
    private Byte gender;
    private String address;

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
