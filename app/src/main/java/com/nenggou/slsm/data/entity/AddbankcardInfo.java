package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/23.
 */

public class AddbankcardInfo {
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
    @SerializedName("aid")
    private String aid;
    @SerializedName("id")
    private String id;

    public String getCardname() {
        return cardname;
    }

    public void setCardname(String cardname) {
        this.cardname = cardname;
    }

    public String getCardbank() {
        return cardbank;
    }

    public void setCardbank(String cardbank) {
        this.cardbank = cardbank;
    }

    public String getCarddbank() {
        return carddbank;
    }

    public void setCarddbank(String carddbank) {
        this.carddbank = carddbank;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }

    public String getTelbank() {
        return telbank;
    }

    public void setTelbank(String telbank) {
        this.telbank = telbank;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
