package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/23.
 */

public class IntercourseRecordInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<IRItemInfo> irItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<IRItemInfo> getIrItemInfos() {
        return irItemInfos;
    }

    public void setIrItemInfos(List<IRItemInfo> irItemInfos) {
        this.irItemInfos = irItemInfos;
    }
}
