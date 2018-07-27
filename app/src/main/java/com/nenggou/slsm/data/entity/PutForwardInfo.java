package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/26.
 */

public class PutForwardInfo {
    @SerializedName("date")
    private String date;
    @SerializedName("allmoney")
    private String allmoney;
    @SerializedName("list")
    private List<PutForwardItem> putForwardItems;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(String allmoney) {
        this.allmoney = allmoney;
    }

    public List<PutForwardItem> getPutForwardItems() {
        return putForwardItems;
    }

    public void setPutForwardItems(List<PutForwardItem> putForwardItems) {
        this.putForwardItems = putForwardItems;
    }
}
