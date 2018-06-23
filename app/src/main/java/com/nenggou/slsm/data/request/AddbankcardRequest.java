package com.nenggou.slsm.data.request;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/23.
 */

public class AddbankcardRequest {
    //持卡人
    @SerializedName("cardname")
    private String cardname;
    //开户行
    @SerializedName("cardbank")
    private String cardbank;
    //支行
    @SerializedName("carddbank")
    private String carddbank;
    //卡号
    @SerializedName("cardno")
    private String cardno;
    //开卡手机号
    @SerializedName("telbank")
    private String telbank;

    public AddbankcardRequest(String cardname, String cardbank, String carddbank, String cardno, String telbank) {
        this.cardname = cardname;
        this.cardbank = cardbank;
        this.carddbank = carddbank;
        this.cardno = cardno;
        this.telbank = telbank;
    }
}
