package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/25.
 */

public class AvatarRequest {
    @SerializedName("avatar")
    private String avatar;

    public AvatarRequest(String avatar) {
        this.avatar = avatar;
    }
}
