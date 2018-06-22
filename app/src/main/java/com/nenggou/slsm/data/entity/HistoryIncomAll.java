package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class HistoryIncomAll {
    @SerializedName("allTotal")
    private String allTotal;
    @SerializedName("allmoney")
    private String allmoney;
    @SerializedName("allpower")
    private String allpower;

    public String getAllTotal() {
        return allTotal;
    }

    public void setAllTotal(String allTotal) {
        this.allTotal = allTotal;
    }

    public String getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(String allmoney) {
        this.allmoney = allmoney;
    }

    public String getAllpower() {
        return allpower;
    }

    public void setAllpower(String allpower) {
        this.allpower = allpower;
    }
}
