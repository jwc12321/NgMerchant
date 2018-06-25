package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/25.
 */

public class ModifyPasswordRequest {
    @SerializedName("newpwd")
    private String newpwd;
    @SerializedName("old")
    private String old;
    @SerializedName("code")
    private String code;
    @SerializedName("tel")
    private String tel;

    public ModifyPasswordRequest(String newpwd, String old, String code, String tel) {
        this.newpwd = newpwd;
        this.old = old;
        this.code = code;
        this.tel = tel;
    }
}
