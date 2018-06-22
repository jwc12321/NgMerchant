package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/21.
 *
 */

public class DayIncomeRequest {
    @SerializedName("storeid")
    private String storeid;
    @SerializedName("date")
    private String date;
    @SerializedName("page")
    private String page;

    public DayIncomeRequest(String storeid, String date, String page) {
        this.storeid = storeid;
        this.date = date;
        this.page = page;
    }
}
