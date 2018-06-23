package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;


/**
 * Created by JWC on 2018/6/22.
 */

public class EnergyInfo {
    @SerializedName("sum")
    private String sum;
    @SerializedName("proportion")
    private String proportion;
    @SerializedName("list")
    private EnergyListInfo energyListInfo;

    public String getSum() {
        return sum;
    }

    public void setSum(String sum) {
        this.sum = sum;
    }

    public EnergyListInfo getEnergyListInfo() {
        return energyListInfo;
    }

    public void setEnergyListInfo(EnergyListInfo energyListInfo) {
        this.energyListInfo = energyListInfo;
    }

    public String getProportion() {
        return proportion;
    }

    public void setProportion(String proportion) {
        this.proportion = proportion;
    }
}
