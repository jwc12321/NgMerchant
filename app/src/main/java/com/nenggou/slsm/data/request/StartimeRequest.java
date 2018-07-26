package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/26.
 */

public class StartimeRequest {
    @SerializedName("starttime")
    private String starttime;

    public StartimeRequest(String starttime) {
        this.starttime = starttime;
    }
}
