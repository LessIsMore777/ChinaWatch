package com.waterworld.watch.login.event;

/**
 * Created by nhuan
 * Time:2018/12/20.
 */

public class AutoLoginEvent {
    private int Auto = 0; //0是否正在连接中,1是成功，2是失败
    private String message ;

    public int getAuto() {
        return Auto;
    }

    public void setAuto(int auto) {
        Auto = auto;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
