package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/7/27.
 */

public class RankingListInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<RankingInfo> rankingInfos;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<RankingInfo> getRankingInfos() {
        return rankingInfos;
    }

    public void setRankingInfos(List<RankingInfo> rankingInfos) {
        this.rankingInfos = rankingInfos;
    }
}
