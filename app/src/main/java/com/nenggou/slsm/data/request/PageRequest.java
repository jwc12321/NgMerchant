package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/23.
 */

public class PageRequest {
    @SerializedName("page")
    private String page;

    public PageRequest(String page) {
        this.page = page;
    }
}
