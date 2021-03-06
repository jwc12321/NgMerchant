package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/25.
 */

public class PutForwardRequest {
    @SerializedName("amount")
    private String amount;
    @SerializedName("type")
    private String type;
    @SerializedName("bankid")
    private String bankid;
    @SerializedName("paypassword")
    private String paypassword;

    public PutForwardRequest(String amount, String type, String bankid, String paypassword) {
        this.amount = amount;
        this.type = type;
        this.bankid = bankid;
        this.paypassword = paypassword;
    }
}
