package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/26.
 */

public class IdRequest {
    @SerializedName("id")
    private String id;

    public IdRequest(String id) {
        this.id = id;
    }
}
