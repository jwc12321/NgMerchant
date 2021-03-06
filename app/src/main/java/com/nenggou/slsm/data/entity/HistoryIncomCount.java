package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/22.
 */

public class HistoryIncomCount {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<HistoryIncomeItem> historyIncomeItems;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<HistoryIncomeItem> getHistoryIncomeItems() {
        return historyIncomeItems;
    }

    public void setHistoryIncomeItems(List<HistoryIncomeItem> historyIncomeItems) {
        this.historyIncomeItems = historyIncomeItems;
    }
}
