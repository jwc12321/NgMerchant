package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/9/7.
 */

public class FinancingidRequest {
    @SerializedName("financingid")
    private String financingid;

    public FinancingidRequest(String financingid) {
        this.financingid = financingid;
    }
}
