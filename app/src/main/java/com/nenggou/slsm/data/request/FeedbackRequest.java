package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/23.
 */

public class FeedbackRequest {
    @SerializedName("feedbackinfo")
    private String feedbackinfo;

    public FeedbackRequest(String feedbackinfo) {
        this.feedbackinfo = feedbackinfo;
    }
}
