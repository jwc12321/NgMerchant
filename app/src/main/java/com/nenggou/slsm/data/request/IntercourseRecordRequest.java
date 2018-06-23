package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/5/2.
 */

public class IntercourseRecordRequest {
    @SerializedName("page")
    private String page;
    @SerializedName("uid")
    private String uid;

    public IntercourseRecordRequest(String page, String uid) {
        this.page = page;
        this.uid = uid;
    }
}
