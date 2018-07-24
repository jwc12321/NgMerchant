package com.nenggou.slsm.push;


import com.google.gson.annotations.SerializedName;

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
}
