package com.waterworld.watch.mine.bean;

/**
 * 编写者：Created by SunnyTang
 * 时间：2019/1/7 10:32
 * 主要作用：消息通知实体类
 */
public class MessageBean {

    private String content;//消息内容
    private String time;//消息时间
    private boolean isRead;//消息是否阅读(小红点)

    public MessageBean(){

    }

    public MessageBean(String content, String time, boolean isRead) {
        this.content = content;
        this.time = time;
        this.isRead = isRead;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }
}
