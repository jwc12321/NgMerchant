package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by JWC on 2018/6/22.
 */

public class EnergyListInfo {
    @SerializedName("current_page")
    private String currentPage;
    @SerializedName("data")
    private List<EnergyDetailInfo> energyDetailInfos;
}
