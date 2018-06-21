package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/21.
 *
 */

public class DayIncomeRequest {
    @SerializedName("date")
    private String date;
    @SerializedName("page")
    private String page;

    public DayIncomeRequest(String date, String page) {
        this.date = date;
        this.page = page;
    }
}
