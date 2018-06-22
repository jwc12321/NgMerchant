package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class HistoryIncomeItem {
    @SerializedName("date")
    private String date;
    @SerializedName("countTotal")
    private String countTotal;
    @SerializedName("allmoney")
    private String allmoney;
    @SerializedName("allpower")
    private String allpower;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCountTotal() {
        return countTotal;
    }

    public void setCountTotal(String countTotal) {
        this.countTotal = countTotal;
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
