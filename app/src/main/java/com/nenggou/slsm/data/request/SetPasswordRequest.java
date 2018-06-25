package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/25.
 */

public class SetPasswordRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("code")
    private String code;
    @SerializedName("newpwd")
    private String newpwd;

    public SetPasswordRequest(String tel, String code, String newpwd) {
        this.tel = tel;
        this.code = code;
        this.newpwd = newpwd;
    }
}
