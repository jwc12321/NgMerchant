package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/9/7.
 */

public class PayFcOrderInfo implements Serializable {
    //钱包
    @SerializedName("qianbao")
    private String qianbao;
    //余额
    @SerializedName("yue")
    private String yue;
    //最低
    @SerializedName("purchaseprice")
    private String purchaseprice;
    //最高
    @SerializedName("restrictprice")
    private String restrictprice;

    public String getQianbao() {
        return qianbao;
    }

    public void setQianbao(String qianbao) {
        this.qianbao = qianbao;
    }

    public String getYue() {
        return yue;
    }

    public void setYue(String yue) {
        this.yue = yue;
    }

    public String getPurchaseprice() {
        return purchaseprice;
    }

    public void setPurchaseprice(String purchaseprice) {
        this.purchaseprice = purchaseprice;
    }

    public String getRestrictprice() {
        return restrictprice;
    }

    public void setRestrictprice(String restrictprice) {
        this.restrictprice = restrictprice;
    }
}
