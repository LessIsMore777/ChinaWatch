package com.waterworld.watch.mine.event;

/**
 * Created by nhuan
 * Time:2019/1/9.
 */

public class AvatarEvent {
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
