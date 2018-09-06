package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/9/6.
 */

public class FinancingListRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("pricetype")
    private String pricetype;

    public FinancingListRequest(String page, String pricetype) {
        this.page = page;
        this.pricetype = pricetype;
    }
}
