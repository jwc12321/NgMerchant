package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/27.
 */

public class RIncomeInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<RIncomeItemInfo> rIncomeItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<RIncomeItemInfo> getrIncomeItemInfos() {
        return rIncomeItemInfos;
    }

    public void setrIncomeItemInfos(List<RIncomeItemInfo> rIncomeItemInfos) {
        this.rIncomeItemInfos = rIncomeItemInfos;
    }
}
