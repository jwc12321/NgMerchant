package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class HistoryIncomeRequest {
    @SerializedName("storeid")
    private String storeid;
    @SerializedName("startTime")
    private String startTime;
    @SerializedName("endTime")
    private String endTime;
    @SerializedName("page")
    private String page;

    public HistoryIncomeRequest(String storeid, String startTime, String endTime, String page) {
        this.storeid = storeid;
        this.startTime = startTime;
        this.endTime = endTime;
        this.page = page;
    }
}
