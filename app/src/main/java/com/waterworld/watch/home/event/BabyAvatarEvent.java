package com.waterworld.watch.home.event;

/**
 * Created by nhuan
 * Time:2019/2/27.
 */

public class BabyAvatarEvent {
    //图片类型: 0 自定义图片 1 系统图片
    private int type;
    private String name;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
