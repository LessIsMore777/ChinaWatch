package com.waterworld.watch.login.bean;

import com.waterworld.watch.common.net.BaseResultBean;

/**
 * Created by nhuan
 * Time:2018/12/14.
 */

public class LoginResponse extends BaseResultBean{

    public String data;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
