package com.waterworld.watch.location.bean;

/**
 * Created by nhuan
 * Time:2019/1/22.
 */

public class ElectronicFenceBean {

    public ElectronicFenceBean(String fence_name,String remind_way,String safety_range,Boolean enable){
        this.fence_name = fence_name;
        this.remind_way = remind_way;
        this.safety_range = safety_range;
        this.enable = enable;
    }

    private String fence_name;
    private String remind_way;
    private String safety_range;
    private boolean enable;

    public String getFence_name() {
        return fence_name;
    }

    public void setFence_name(String fence_name) {
        this.fence_name = fence_name;
    }

    public String getRemind_way() {
        return remind_way;
    }

    public void setRemind_way(String remind_way) {
        this.remind_way = remind_way;
    }

    public String getSafety_range() {
        return safety_range;
    }

    public void setSafety_range(String safety_range) {
        this.safety_range = safety_range;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }
}
