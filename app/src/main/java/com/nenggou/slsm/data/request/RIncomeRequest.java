package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/27.
 */

public class RIncomeRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("uid")
    private String uid;
    @SerializedName("type")
    private String type;
    @SerializedName("starttime")
    private String starttime;

    public RIncomeRequest(String page, String uid, String type, String starttime) {
        this.page = page;
        this.uid = uid;
        this.type = type;
        this.starttime = starttime;
    }
}
