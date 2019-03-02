package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;

/**
 * Created by nhuan
 * Time:2019/3/1.
 */

public class SkillManagerBean {
    private String skillName;
    private Drawable skillImage;

    public SkillManagerBean(String skillName, Drawable skillImage) {
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
}
