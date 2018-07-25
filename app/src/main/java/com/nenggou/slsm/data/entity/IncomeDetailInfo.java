package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class IncomeDetailInfo {
    @SerializedName("nickname")
    private String nickname;
    @SerializedName("avatar")
    private String avatar;
    @SerializedName("title")
    private String title;
    @SerializedName("allprice")
    private String allprice;
    @SerializedName("price")
    private String price;
    @SerializedName("power")
    private String power;
    @SerializedName("status")
    private String status;
    @SerializedName("orderno")
    private String orderno;
    @SerializedName("paytype")
    private String paytype;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("quannum")
    private String quannum;
    @SerializedName("uid")
    private String uid;
    @SerializedName("aprice")
    private String aprice;
    @SerializedName("apower")
    private String apower;
    @SerializedName("bpower")
    private String bpower;
    @SerializedName("power_rate")
    private String powerRate;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAllprice() {
        return allprice;
    }

    public void setAllprice(String allprice) {
        this.allprice = allprice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOrderno() {
        return orderno;
    }

    public void setOrderno(String orderno) {
        this.orderno = orderno;
    }

    public String getPaytype() {
        return paytype;
    }

    public void setPaytype(String paytype) {
        this.paytype = paytype;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getQuannum() {
        return quannum;
    }

    public void setQuannum(String quannum) {
        this.quannum = quannum;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAprice() {
        return aprice;
    }

    public void setAprice(String aprice) {
        this.aprice = aprice;
    }

    public String getApower() {
        return apower;
    }

    public void setApower(String apower) {
        this.apower = apower;
    }

    public String getBpower() {
        return bpower;
    }

    public void setBpower(String bpower) {
        this.bpower = bpower;
    }

    public String getPowerRate() {
        return powerRate;
    }

    public void setPowerRate(String powerRate) {
        this.powerRate = powerRate;
    }
}
