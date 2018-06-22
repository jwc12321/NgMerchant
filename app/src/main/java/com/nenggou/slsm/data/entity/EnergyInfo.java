package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/22.
 */

public class EnergyInfo {
    @SerializedName("sum")
    private List<EnergySum> energySums;
    @SerializedName("list")
    private EnergyListInfo energyListInfo;

    public List<EnergySum> getEnergySums() {
        return energySums;
    }

    public void setEnergySums(List<EnergySum> energySums) {
        this.energySums = energySums;
    }

    public EnergyListInfo getEnergyListInfo() {
        return energyListInfo;
    }

    public void setEnergyListInfo(EnergyListInfo energyListInfo) {
        this.energyListInfo = energyListInfo;
    }
}
