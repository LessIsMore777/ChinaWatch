package com.waterworld.watch.login.bean;


/**
 * Created by nhuan
 * Time:2018/12/12.
 */

public class LoginBean {

    private static LoginBean loginBean;

    private String username;
    private String password;
    private int user_id;
    private String session_id;

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

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getSession_id() {
        return session_id;
    }

    public void setSession_id(String session_id) {
        this.session_id = session_id;
    }
}
