package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;


/**
 * Created by JWC on 2018/6/22.
 */

public class EnergyDetailInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("cash")
    private String cash;
    @SerializedName("power")
    private String power;
    @SerializedName("created_at")
    private String created_at;
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
    @SerializedName("cashtixianid")
    private String cashtixianid;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCash() {
        return cash;
    }

    public void setCash(String cash) {
        this.cash = cash;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
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

    public String getPayoutid() {
        return payoutid;
    }

    public void setPayoutid(String payoutid) {
        this.payoutid = payoutid;
    }

    public String getCashtixianid() {
        return cashtixianid;
    }

    public void setCashtixianid(String cashtixianid) {
        this.cashtixianid = cashtixianid;
    }
}
