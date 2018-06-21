package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;

/**
 * Created by JWC on 2018/6/21.
 */

public class AppstoreInfo {
    @SerializedName("z_pics")
    private String zPics;
    @SerializedName("id")
    private String id;
    @SerializedName("title")
    private String title;
    @SerializedName("shouru")
    private Shouru shouru;

    public String getzPics() {
        return zPics;
    }

    public void setzPics(String zPics) {
        this.zPics = zPics;
    }

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

    public Shouru getShouru() {
        return shouru;
    }

    public void setShouru(Shouru shouru) {
        this.shouru = shouru;
    }
}
