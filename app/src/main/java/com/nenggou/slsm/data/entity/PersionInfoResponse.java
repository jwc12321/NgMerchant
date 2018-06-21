package com.nenggou.slsm.data.entity;

import com.google.gson.annotations.SerializedName;


/**
 * Created by JWC on 2018/4/19.
 */

public class PersionInfoResponse {
    @SerializedName("id")
    private String id;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    //头像
    @SerializedName("avatar")
    private String avatar;
    //商家
    @SerializedName("cname")
    private String cname;
    //审核时间
    @SerializedName("verified_at")
    private String verified_at;
    @SerializedName("token")
    private String token;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public String getVerified_at() {
        return verified_at;
    }

    public void setVerified_at(String verified_at) {
        this.verified_at = verified_at;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
