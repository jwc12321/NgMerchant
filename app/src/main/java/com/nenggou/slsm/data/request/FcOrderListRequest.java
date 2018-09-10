package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/9/10.
 */

public class FcOrderListRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("status")
    private String status;

    public FcOrderListRequest(String page, String status) {
        this.page = page;
        this.status = status;
    }
}
