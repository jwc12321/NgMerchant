package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/4/17.
 */

public class PasswordLoginRequest {
    @SerializedName("tel")
    private String tel;
    @SerializedName("password")
    private String password;

    public PasswordLoginRequest(String tel, String password) {
        this.tel = tel;
        this.password = password;
    }
}
