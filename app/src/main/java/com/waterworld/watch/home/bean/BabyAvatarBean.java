package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;


public class BabyAvatarBean {

    private Drawable avatar;
    private boolean isChecked;

    public BabyAvatarBean(Drawable avatar,boolean isChecked) {
        this.isChecked = isChecked;
        this.avatar = avatar;
    }

    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
