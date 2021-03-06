package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/9/10.
 */

public class TurnOutRecord {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<TurnOutRecordItem> turnOutRecordItems;

    public String getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(String currentPage) {
        this.currentPage = currentPage;
    }

    public List<TurnOutRecordItem> getTurnOutRecordItems() {
        return turnOutRecordItems;
    }

    public void setTurnOutRecordItems(List<TurnOutRecordItem> turnOutRecordItems) {
        this.turnOutRecordItems = turnOutRecordItems;
    }
}
