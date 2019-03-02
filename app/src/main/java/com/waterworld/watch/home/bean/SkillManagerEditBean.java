package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;

public class SkillManagerEditBean {

    private String skillName;//功能名称
    private Drawable skillImage;//功能图标
    private Drawable skillAddOrDelete;//增加或删除的图标

    public SkillManagerEditBean(String skillName, Drawable skillImage, Drawable skillAddOrDelete) {
        this.skillName = skillName;
        this.skillImage = skillImage;
        this.skillAddOrDelete = skillAddOrDelete;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public Drawable getSkillImage() {
        return skillImage;
    }

    public void setSkillImage(Drawable skillImage) {
        this.skillImage = skillImage;
    }

    public Drawable getSkillAddOrDelete() {
        return skillAddOrDelete;
    }

    public void setSkillAddOrDelete(Drawable skillAddOrDelete) {
        this.skillAddOrDelete = skillAddOrDelete;
    }
}
