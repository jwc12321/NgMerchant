package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/21.
 *
 */

public class RdIncomeRequest {
    @SerializedName("time")
    private String time;
    @SerializedName("page")
    private String page;

    public RdIncomeRequest(String time, String page) {
        this.time = time;
        this.page = page;
    }
}
