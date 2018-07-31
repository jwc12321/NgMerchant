package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/7/31.
 */

public class CouponInfo {
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("price")
    private String price;
    @SerializedName("total")
    private String total;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("endtime")
    private String endtime;
    @SerializedName("condition")
    private String condition;
    @SerializedName("pic")
    private String pic;
    @SerializedName("status")
    private String status;
    @SerializedName("suitbusiness")
    private String suitbusiness;
    @SerializedName("updated_at")
    private String updated_at;
    @SerializedName("starttime")
    private String starttime;
    @SerializedName("validday")
    private String validday;
    @SerializedName("least_cost")
    private String leastCost;
    @SerializedName("deleted_at")
    private String deletedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSuitbusiness() {
        return suitbusiness;
    }

    public void setSuitbusiness(String suitbusiness) {
        this.suitbusiness = suitbusiness;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getValidday() {
        return validday;
    }

    public void setValidday(String validday) {
        this.validday = validday;
    }

    public String getLeastCost() {
        return leastCost;
    }

    public void setLeastCost(String leastCost) {
        this.leastCost = leastCost;
    }

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
        this.deletedAt = deletedAt;
    }
}
