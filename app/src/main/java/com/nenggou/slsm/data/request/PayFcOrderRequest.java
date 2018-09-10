package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/9/10.
 */

public class PayFcOrderRequest {
    @SerializedName("financingid")
    private String financingid;
    @SerializedName("type")
    private String type;
    @SerializedName("paymoney")
    private String paymoney;
    @SerializedName("paypassword")
    private String paypassword;

    public PayFcOrderRequest(String financingid, String type, String paymoney, String paypassword) {
        this.financingid = financingid;
        this.type = type;
        this.paymoney = paymoney;
        this.paypassword = paypassword;
    }
}
