package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/7 19:56
 * 主要作用：
 */
public class AvatarBean {

    private Drawable avatar;

    public AvatarBean(Drawable avatar) {
        this.avatar = avatar;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }
}
