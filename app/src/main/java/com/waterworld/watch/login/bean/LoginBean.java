package com.waterworld.watch.login.bean;


/**
 * Created by nhuan
 * Time:2018/12/12.
 */

public class LoginBean {

    private static LoginBean loginBean;

    private String username;
    private String password;

    public static LoginBean getInstance() {
        if (loginBean == null){
            synchronized (LoginBean.class){
                if (loginBean == null){
                    loginBean = new LoginBean();
                }
            }
        }
        return loginBean;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
