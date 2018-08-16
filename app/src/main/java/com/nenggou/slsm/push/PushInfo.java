package com.nenggou.slsm.push;


import com.google.gson.annotations.SerializedName;
import com.nenggou.slsm.data.request.PageRequest;

import java.io.Serializable;

/**
 * Created by JWC on 2017/5/2.
 */

public class PushInfo implements Serializable {
    @SerializedName("nowprice")
    private String nowprice;
    @SerializedName("price")
    private String price;
    @SerializedName("total")
    private String total;
    @SerializedName("type")
    private String type;
    @SerializedName("username")
    private String username;
    @SerializedName("userid")
    private String userid;
    @SerializedName("storename")
    private String storename;
    @SerializedName("paytime")
    private String paytime;
    @SerializedName("useravatar")
    private String useravatar;
    @SerializedName("apower")
    private String apower;
    @SerializedName("aprice")
    private String aprice;

    public String getNowprice() {
        return nowprice;
    }

    public void setNowprice(String nowprice) {
        this.nowprice = nowprice;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getStorename() {
        return storename;
    }

    public void setStorename(String storename) {
        this.storename = storename;
    }

    public String getPaytime() {
        return paytime;
    }

    public void setPaytime(String paytime) {
        this.paytime = paytime;
    }

    public String getUseravatar() {
        return useravatar;
    }

    public void setUseravatar(String useravatar) {
        this.useravatar = useravatar;
    }

    public String getApower() {
        return apower;
    }

    public void setApower(String apower) {
        this.apower = apower;
    }

    public String getAprice() {
        return aprice;
    }

    public void setAprice(String aprice) {
        this.aprice = aprice;
    }
}
