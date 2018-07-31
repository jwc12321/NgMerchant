package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/31.
 */

public class FinancingInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<FinancingItemInfo> financingItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<FinancingItemInfo> getFinancingItemInfos() {
        return financingItemInfos;
    }

    public void setFinancingItemInfos(List<FinancingItemInfo> financingItemInfos) {
        this.financingItemInfos = financingItemInfos;
    }
}
