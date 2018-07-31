package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/31.
 */

public class SendOutCouponRequest {
    @SerializedName("uid")
    private String uid;
    @SerializedName("quanid")
    private String quanid;

    public SendOutCouponRequest(String uid, String quanid) {
        this.uid = uid;
        this.quanid = quanid;
    }
}
