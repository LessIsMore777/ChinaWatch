package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;


public class WatchOtherUserBean {

    private int userID;//其他用户ID
    private Drawable userAvatar;//其他用户头像
    private String userName;//其他用户名称
    private String userRelation;//其他用户与使用手表的儿童之间关系（比如userName是手表使用者的哥哥）

    public WatchOtherUserBean(int userID, Drawable userAvatar, String userName, String userRelation) {
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
