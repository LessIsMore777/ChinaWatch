package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/5 14:34
 * 主要作用：
 */
public class AddSkillBean {

    private String skillName;//功能名称
    private Drawable skillImage;//功能图标
    private Drawable skillAddOrDelete;//增加或删除的图标
    private boolean isEdit = false;//是否开启了编辑模式,默认为关闭

    public AddSkillBean(String skillName, Drawable skillImage, Drawable skillAddOrDelete, boolean isEdit) {
        this.skillName = skillName;
        this.skillImage = skillImage;
        this.skillAddOrDelete = skillAddOrDelete;
        this.isEdit = isEdit;
    }

    public AddSkillBean(String skillName, Drawable skillImage){
        this.skillName = skillName;
        this.skillImage = skillImage;
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

    public boolean isEdit() {
        return isEdit;
    }

    public void setEdit(boolean edit) {
        isEdit = edit;
    }
}
