package com.waterworld.watch.mine.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by nhuan
 * Time:2018/12/28.
 */

public class AvatarBean {
    private Drawable avatar;
    private boolean isSelect;

    public AvatarBean(Drawable avatar, Boolean isSelect){
        this.avatar = avatar;
        this.isSelect = isSelect;
    }
    public Drawable getAvatar() {
        return avatar;
    }

    public void setAvatar(Drawable avatar) {
        this.avatar = avatar;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }
}
