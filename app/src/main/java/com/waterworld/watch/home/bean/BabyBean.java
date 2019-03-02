package com.waterworld.watch.home.bean;

public class BabyBean {
    private Object babyAvatar;//头像链接
    private String babyName;//名字

    public BabyBean(Object babyAvatar, String babyName) {
        this.babyAvatar = babyAvatar;
        this.babyName = babyName;
    }

    public Object getBabyAvatar() {
        return babyAvatar;
    }

    public void setBabyAvatar(Object babyAvatar) {
        this.babyAvatar = babyAvatar;
    }

    public String getBabyName() {
        return babyName;
    }

    public void setBabyName(String babyName) {
        this.babyName = babyName;
    }

}
