package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/21.
 */

public class IncomeList {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<InComeInfo> inComeInfos;
    @SerializedName("total")
    private String total;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<InComeInfo> getInComeInfos() {
        return inComeInfos;
    }

    public void setInComeInfos(List<InComeInfo> inComeInfos) {
        this.inComeInfos = inComeInfos;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }
}
