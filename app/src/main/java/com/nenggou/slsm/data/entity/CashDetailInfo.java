package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class CashDetailInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("xianjin")
    private String xianjin;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("types")
    private String types;
    @SerializedName("username")
    private String username;
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("payoutid")
    private String payoutid;

    public String getPayoutid() {
        return payoutid;
    }

    public void setPayoutid(String payoutid) {
        this.payoutid = payoutid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getXianjin() {
        return xianjin;
    }

    public void setXianjin(String xianjin) {
        this.xianjin = xianjin;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
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
}
