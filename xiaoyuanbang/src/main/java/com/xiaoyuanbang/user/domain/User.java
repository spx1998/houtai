package com.xiaoyuanbang.user.domain;

import java.io.Serializable;

public class User implements Serializable {

    private static final long serialVersionUID = 6308987888585366666L;
    private int id;
    private String username;
    private String gender;
    private int qqid;
    private String wxid;
    private int phone;
    private String school;
    private String openid;
    private String state;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getQqid() {
        return qqid;
    }

    public void setQqid(int qqid) {
        this.qqid = qqid;
    }

    public String getWxid() {
        return wxid;
    }

    public void setWxid(String wxid) {
        this.wxid = wxid;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }
}
