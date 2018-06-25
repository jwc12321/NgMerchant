package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/25.
 */

public class TextRequest {
    @SerializedName("text")
    private String text;

    public TextRequest(String text) {
        this.text = text;
    }
}
