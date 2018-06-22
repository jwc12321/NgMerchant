package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/22.
 */

public class HistoryIncomInfo {
    @SerializedName("all")
    private List<HistoryIncomAll> historyIncomAlls;
    @SerializedName("count")
    private HistoryIncomCount historyIncomCount;

    public List<HistoryIncomAll> getHistoryIncomAlls() {
        return historyIncomAlls;
    }

    public void setHistoryIncomAlls(List<HistoryIncomAll> historyIncomAlls) {
        this.historyIncomAlls = historyIncomAlls;
    }

    public HistoryIncomCount getHistoryIncomCount() {
        return historyIncomCount;
    }

    public void setHistoryIncomCount(HistoryIncomCount historyIncomCount) {
        this.historyIncomCount = historyIncomCount;
    }
}
