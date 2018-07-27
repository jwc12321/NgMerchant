package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/27.
 */

public class CRankingRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("type")
    private String type;
    @SerializedName("starttime")
    private String starttime;

    public CRankingRequest(String page, String type, String starttime) {
        this.page = page;
        this.type = type;
        this.starttime = starttime;
    }
}
