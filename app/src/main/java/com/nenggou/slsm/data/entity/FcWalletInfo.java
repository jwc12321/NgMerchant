package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/9/10.
 */

public class FcWalletInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("aid")
    private String aid;
    @SerializedName("power")
    private String power;
    @SerializedName("price")
    private String price;
    @SerializedName("powermytotal")
    private String powermytotal;
    @SerializedName("pricemytotal")
    private String pricemytotal;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPowermytotal() {
        return powermytotal;
    }

    public void setPowermytotal(String powermytotal) {
        this.powermytotal = powermytotal;
    }

    public String getPricemytotal() {
        return pricemytotal;
    }

    public void setPricemytotal(String pricemytotal) {
        this.pricemytotal = pricemytotal;
    }
}
