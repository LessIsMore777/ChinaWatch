package com.waterworld.watch.common.bean;

/**
 * Created by nhuan
 * Time:2019/1/10.
 * 当前手表用户信息
 * 保持唯一性
 */

public class WatchUserInfoBean {

    private static WatchUserInfoBean watchUserInfoBean;

    private WatchUserInfo watchUserInfo;
    private WatchInfo watchInfo;

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

    public WatchUserInfo getWatchUserInfo() {
        return watchUserInfo;
    }

    public void setWatchUserInfo(WatchUserInfo watchUserInfo) {
        this.watchUserInfo = watchUserInfo;
    }

    public WatchInfo getWatchInfo() {
        return watchInfo;
    }

    public void setWatchInfo(WatchInfo watchInfo) {
        this.watchInfo = watchInfo;
    }

    public class WatchUserInfo {
        private String birthday;
        private String classes;
        private String createTime;
        private String functionSign;
        private String grade;
        private String head;
        private String height;
        private String name;
        private String phone;
        private int sex;
        private String updateTime;
        private int watchId;
        private String weight;


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

        public String getFunctionSign() {
            return functionSign;
        }

        public void setFunctionSign(String functionSign) {
            this.functionSign = functionSign;
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

    public class WatchInfo {
        private String createTime;
        private String dv;
        private String imei;
        private String model;
        private String sd;
        private String watchId;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getDv() {
            return dv;
        }

        public void setDv(String dv) {
            this.dv = dv;
        }

        public String getImei() {
            return imei;
        }

        public void setImei(String imei) {
            this.imei = imei;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getSd() {
            return sd;
        }

        public void setSd(String sd) {
            this.sd = sd;
        }

        public String getWatchId() {
            return watchId;
        }

        public void setWatchId(String watchId) {
            this.watchId = watchId;
        }
    }

}
