package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/22.
 */

public class CashDetailList {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<CashDetailInfo> cashDetailInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<CashDetailInfo> getCashDetailInfos() {
        return cashDetailInfos;
    }

    public void setCashDetailInfos(List<CashDetailInfo> cashDetailInfos) {
        this.cashDetailInfos = cashDetailInfos;
    }
}
