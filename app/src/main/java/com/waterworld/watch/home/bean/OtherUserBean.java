package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/6 11:25
 * 主要作用：与手表绑定的其他用户实体类
 */
public class OtherUserBean {

    private int userID;//其他用户ID
    private Drawable userAvatar;//其他用户头像
    private String userName;//其他用户名称
    private String userRelation;//其他用户与使用手表的儿童之间关系（比如userName是手表使用者的哥哥）

    public OtherUserBean(int userID, Drawable userAvatar, String userName, String userRelation) {
        this.userID = userID;
        this.userAvatar = userAvatar;
        this.userName = userName;
        this.userRelation = userRelation;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserRelation() {
        return userRelation;
    }

    public void setUserRelation(String userRelation) {
        this.userRelation = userRelation;
    }

    public Drawable getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(Drawable userAvatar) {
        this.userAvatar = userAvatar;
    }
}
