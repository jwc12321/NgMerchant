package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/8/14.
 */

public class RdListInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("username")
    private String username;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("addtime")
    private String addtime;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("price")
    private String price;
    @SerializedName("power")
    private String power;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getAddtime() {
        return addtime;
    }

    public void setAddtime(String addtime) {
        this.addtime = addtime;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
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
}
