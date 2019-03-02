package com.waterworld.watch.common.bean;


/**
 * Created by nhuan
 * Time:2019/1/10.
 * 当前用户信息
 * 保持唯一性
 */

public class UserInfoBean {

    private static UserInfoBean userInfoBean;
    private WatchUser watchUser;
    private User user;
    public static UserInfoBean getInstance() {
        if (userInfoBean == null) {
            synchronized (UserInfoBean.class){
                if (userInfoBean == null) {
                    userInfoBean = new UserInfoBean();
                }
            }
        }
        return userInfoBean;
    }

    @Override
    public String toString() {
        return watchUser.getHead().toString();
    }
    public WatchUser getWatchUser() {
        return watchUser;
    }

    public void setWatchUser(WatchUser watchUser) {
        this.watchUser = watchUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    //当前登录手表用户信息
    public class WatchUser {
        private String head;  //头像地址，自定义图像下载地址需拼接 baseUrl/resources/watch/+head
        private String phone;
        private int watchId;
        private String name;
        private String userRelation;
        private int userRole;
        private int userId;

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getWatchId() {
            return watchId;
        }

        public void setWatchId(int watchId) {
            this.watchId = watchId;
        }

        public String getUserRelation() {
            return userRelation;
        }

        public void setUserRelation(String userRelation) {
            this.userRelation = userRelation;
        }

        public int getUserRole() {
            return userRole;
        }

        public void setUserRole(int userRole) {
            this.userRole = userRole;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        @Override
        public String toString() {
            return super.toString();
        }
    }
    //登录用户信息
    public class User {
        private String address;
        private String createTime;
        private String head;  //头像地址，自定义图像下载地址需拼接 baseUrl/resources/watch/+head
        private int loginDeviceType;
        private String name;
        private int operatingWatch;
        private String password;
        private String phone;
        private int sex;      //性别 1-男，2-女,0-不明
        private int status;
        private String updateTime;
        private int userId;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOperatingWatch() {
            return operatingWatch;
        }

        public void setOperatingWatch(int operatingWatch) {
            this.operatingWatch = operatingWatch;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getLoginDeviceType() {
            return loginDeviceType;
        }

        public void setLoginDeviceType(int loginDeviceType) {
            this.loginDeviceType = loginDeviceType;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

}
