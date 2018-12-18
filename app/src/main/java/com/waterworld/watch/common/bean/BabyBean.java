package com.waterworld.watch.common.bean;

import android.graphics.drawable.Drawable;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/29 18:31
 * 主要作用：
 */
public class BabyBean {

    private int babyID;//宝贝id
    private Drawable babyAvatar;//宝贝头像
    private String babyName;//宝贝名称

    public BabyBean(int babyID, Drawable babyAvatar, String babyName) {
        this.babyID = babyID;
        this.babyAvatar = babyAvatar;
        this.babyName = babyName;
    }

    public BabyBean(Drawable babyAvatar, String babyName) {
        this.babyAvatar = babyAvatar;
        this.babyName = babyName;
    }

    public Drawable getBabyAvatar() {
        return babyAvatar;
    }

    public void setBabyAvatar(Drawable babyAvatar) {
        this.babyAvatar = babyAvatar;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

    public int getBabyID() {
        return babyID;
    }

    public void setBabyID(int babyID) {
        this.babyID = babyID;
    }
}
