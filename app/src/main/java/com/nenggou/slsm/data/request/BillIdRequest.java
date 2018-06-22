package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/22.
 */

public class BillIdRequest {
    @SerializedName("billid")
    private String billid;

    public BillIdRequest(String billid) {
        this.billid = billid;
    }
}
