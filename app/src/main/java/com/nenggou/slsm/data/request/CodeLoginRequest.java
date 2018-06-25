package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/25.
 */

public class CodeLoginRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("code")
    private String code;

    public CodeLoginRequest(String tel, String code) {
        this.tel = tel;
        this.code = code;
    }
}
