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

    public PutForwardRequest(String amount, String type, String bankid) {
        this.amount = amount;
        this.type = type;
        this.bankid = bankid;
    }
}
