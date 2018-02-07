package net.zriot.ebike.model.user;

import java.io.Serializable;

public class UserMain implements Serializable {

    private Integer id;

    private String uid;

    private Byte gender;

    private String tel;

    private String avatar;

    private String nickName;

    private Byte isMembership;

    private Integer Money;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Byte getIsMembership() {
        return isMembership;
    }

    public void setIsMembership(Byte isMembership) {
        this.isMembership = isMembership;
    }

    public Integer getMoney() {
        return Money;
    }

    public void setMoney(Integer money) {
        Money = money;
    }
}
