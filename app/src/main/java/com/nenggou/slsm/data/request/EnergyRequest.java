package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class EnergyRequest {
    @SerializedName("type")
    private String type;
    @SerializedName("page")
    private String page;

    public EnergyRequest(String type, String page) {
        this.type = type;
        this.page = page;
    }
}
