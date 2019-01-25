package com.waterworld.watch.common.bean;

/**
 * Created by nhuan
 * Time:2019/1/10.
 * 当前手表用户信息
 * 保持唯一性
 */

public class WatchUserInfoBean {

    private static WatchUserInfoBean watchUserInfoBean;

    public static WatchUserInfoBean getInstance() {
        if (watchUserInfoBean == null) {
            synchronized (UserInfoBean.class){
                if (watchUserInfoBean == null) {
                    watchUserInfoBean = new WatchUserInfoBean();
                }
            }
        }
        return watchUserInfoBean;
    }

    private String birthday;
    private String classes;
    private String createTime;
    private String grade;
    private String head;
    private String height;
    private String name;
    private String phone;
    private int sex;
    private String updateTime;
    private int watchId;
    private String weight;

    public static WatchUserInfoBean getWatchUserInfoBean() {
        return watchUserInfoBean;
    }

    public static void setWatchUserInfoBean(WatchUserInfoBean watchUserInfoBean) {
        WatchUserInfoBean.watchUserInfoBean = watchUserInfoBean;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public int getWatchId() {
        return watchId;
    }

    public void setWatchId(int watchId) {
        this.watchId = watchId;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }
}
