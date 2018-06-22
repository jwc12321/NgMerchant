package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;


/**
 * Created by JWC on 2018/6/22.
 */

public class CashInfo {
    @SerializedName("xianjin")
    private String xianJin;

    public String getXianJin() {
        return xianJin;
    }

    public void setXianJin(String xianJin) {
        this.xianJin = xianJin;
    }
}
