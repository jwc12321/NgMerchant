package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/4/24.
 * 评价信息
 */

public class EvaluateInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<EvaluateItemInfo> evaluateItemInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<EvaluateItemInfo> getEvaluateItemInfos() {
        return evaluateItemInfos;
    }

    public void setEvaluateItemInfos(List<EvaluateItemInfo> evaluateItemInfos) {
        this.evaluateItemInfos = evaluateItemInfos;
    }
}
