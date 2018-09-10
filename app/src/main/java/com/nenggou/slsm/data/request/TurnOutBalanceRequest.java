package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/9/10.
 */

public class TurnOutBalanceRequest {
    @SerializedName("sum")
    private String sum;
    @SerializedName("type")
    private String type;
    @SerializedName("paypassword")
    private String paypassword;

    public TurnOutBalanceRequest(String sum, String type, String paypassword) {
        this.sum = sum;
        this.type = type;
        this.paypassword = paypassword;
    }
}
