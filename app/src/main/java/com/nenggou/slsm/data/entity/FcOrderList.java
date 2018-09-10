package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/9/10.
 */

public class FcOrderList {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<FinaningOrderItemInfo> finaningOrderItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<FinaningOrderItemInfo> getFinaningOrderItemInfos() {
        return finaningOrderItemInfos;
    }

    public void setFinaningOrderItemInfos(List<FinaningOrderItemInfo> finaningOrderItemInfos) {
        this.finaningOrderItemInfos = finaningOrderItemInfos;
    }
}
