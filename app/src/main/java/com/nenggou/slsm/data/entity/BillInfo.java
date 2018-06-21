package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/21.
 *
 */

public class BillInfo {
    @SerializedName("allmoney")
    private String allmoney;
    @SerializedName("allpower")
    private String allpower;
    @SerializedName("list")
    private IncomeList incomeList;

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

    public IncomeList getIncomeList() {
        return incomeList;
    }

    public void setIncomeList(IncomeList incomeList) {
        this.incomeList = incomeList;
    }
}
