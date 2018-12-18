package com.waterworld.watch.common.net;

/**
 * Created by nhuan
 * Time:2018/12/12.
 * Json parses the corresponding entity in a uniform format encapsulated
 */

public class BaseResultBean {
    private int code;
    private int count;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
