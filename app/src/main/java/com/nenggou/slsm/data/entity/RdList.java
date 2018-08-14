package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/8/14.
 */

public class RdList {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<RdListInfo> rdListInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<RdListInfo> getRdListInfos() {
        return rdListInfos;
    }

    public void setRdListInfos(List<RdListInfo> rdListInfos) {
        this.rdListInfos = rdListInfos;
    }
}
