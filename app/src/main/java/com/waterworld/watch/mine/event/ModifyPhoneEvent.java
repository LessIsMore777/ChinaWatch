package com.waterworld.watch.mine.event;

/**
 * 编写者：Created by SunnyTang
 * 时间：2019/1/10 15:41
 * 主要作用：
 */
public class ModifyPhoneEvent {

    private String phone;

    public ModifyPhoneEvent(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
