package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by JWC on 2018/7/31.
 */

public class FinancingItemInfo implements Serializable{
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    //年利率
    @SerializedName("interestRate")
    private String interestRate;
    //额外利率
    @SerializedName("additional")
    private String additional;
    //周期
    @SerializedName("cycle")
    private String cycle;
    //总金额
    @SerializedName("totalAmount")
    private String totalAmount;
    //剩余
    @SerializedName("surplus")
    private String surplus;
    //利息类型
    @SerializedName("type")
    private String type;
    //手续费
    @SerializedName("servicecharge")
    private String servicecharge;
    @SerializedName("created_at")
    private String createdAt;
    @SerializedName("updated_at")
    private String updatedAt;
    //软删除时间
    @SerializedName("delete_at")
    private String deleteAt;
    @SerializedName("starttime")
    private String starttime;
    @SerializedName("endtime")
    private String endtime;
    //偏差值
    @SerializedName("deviation")
    private String deviation;
    //'0售卖期，1计息期，2返息期，3项目结束'
    @SerializedName("status")
    private String status;
    //存储方式
    @SerializedName("storagetype")
    private String storagetype;
    @SerializedName("additionaltype")
    private String additionaltype;
    //0:能量 1:现金
    @SerializedName("pricetype")
    private String pricetype;

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

    public String getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(String interestRate) {
        this.interestRate = interestRate;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    public String getCycle() {
        return cycle;
    }

    public void setCycle(String cycle) {
        this.cycle = cycle;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSurplus() {
        return surplus;
    }

    public void setSurplus(String surplus) {
        this.surplus = surplus;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getServicecharge() {
        return servicecharge;
    }

    public void setServicecharge(String servicecharge) {
        this.servicecharge = servicecharge;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getDeleteAt() {
        return deleteAt;
    }

    public void setDeleteAt(String deleteAt) {
        this.deleteAt = deleteAt;
    }

    public String getStarttime() {
        return starttime;
    }

    public void setStarttime(String starttime) {
        this.starttime = starttime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getDeviation() {
        return deviation;
    }

    public void setDeviation(String deviation) {
        this.deviation = deviation;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStoragetype() {
        return storagetype;
    }

    public void setStoragetype(String storagetype) {
        this.storagetype = storagetype;
    }

    public String getAdditionaltype() {
        return additionaltype;
    }

    public void setAdditionaltype(String additionaltype) {
        this.additionaltype = additionaltype;
    }

    public String getPricetype() {
        return pricetype;
    }

    public void setPricetype(String pricetype) {
        this.pricetype = pricetype;
    }
}
