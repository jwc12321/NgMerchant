package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/6/23.
 */

public class BankCardInfo implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("cardbank")
    private String cardbank;
    @SerializedName("cardno")
    private String cardno;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCardbank() {
        return cardbank;
    }

    public void setCardbank(String cardbank) {
        this.cardbank = cardbank;
    }

    public String getCardno() {
        return cardno;
    }

    public void setCardno(String cardno) {
        this.cardno = cardno;
    }
}
