package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class CashDate {
    @SerializedName("list")
    private CashDetailList cashDetailList;

    public CashDetailList getCashDetailList() {
        return cashDetailList;
    }

    public void setCashDetailList(CashDetailList cashDetailList) {
        this.cashDetailList = cashDetailList;
    }
}
