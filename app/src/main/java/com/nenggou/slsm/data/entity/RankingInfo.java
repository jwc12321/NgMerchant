package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/27.
 */

public class RankingInfo {
    @SerializedName("allmoney")
    private String allmoney;
    @SerializedName("price")
    private String price;
    @SerializedName("power")
    private String power;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("tel")
    private String tel;
    @SerializedName("uid")
    private String uid;
    @SerializedName("avatar")
    private String avatar;

    public String getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(String allmoney) {
        this.allmoney = allmoney;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
