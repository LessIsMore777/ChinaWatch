package com.waterworld.watch.home.bean;

import android.graphics.drawable.Drawable;

import java.io.Serializable;


public class SkillContactBean implements Serializable {
    private Drawable contactAvatar;//联系人头像
    private String contactName;//联系人名称
    private String contactPhone;//联系人手机号码
    private boolean normalCallPhone;//普通通话权限
    private boolean urgentCallPhone;//紧急通话权限
    private boolean aloneListener;//单项聆听权限

    /**
     * 联系人构造
     * @param contactAvatar 联系人头像
     * @param contactName 联系人名称
     * @param contactPhone 联系人手机号码
     * @param normalCallPhone 普通通话权限
     * @param urgentCallPhone 紧急通话权限
     * @param aloneListener 单项聆听权限
     */
    public SkillContactBean(Drawable contactAvatar, String contactName, String contactPhone, boolean normalCallPhone, boolean urgentCallPhone, boolean aloneListener) {
        this.contactAvatar = contactAvatar;
        this.contactName = contactName;
        this.contactPhone = contactPhone;
        this.normalCallPhone = normalCallPhone;
        this.urgentCallPhone = urgentCallPhone;
        this.aloneListener = aloneListener;
    }

    public Drawable getContactAvatar() {
        return contactAvatar;
    }

    public void setContactAvatar(Drawable contactAvatar) {
        this.contactAvatar = contactAvatar;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    public boolean isNormalCallPhone() {
        return normalCallPhone;
    }

    public void setNormalCallPhone(boolean normalCallPhone) {
        this.normalCallPhone = normalCallPhone;
    }

    public boolean isUrgentCallPhone() {
        return urgentCallPhone;
    }

    public void setUrgentCallPhone(boolean urgentCallPhone) {
        this.urgentCallPhone = urgentCallPhone;
    }

    public boolean isAloneListener() {
        return aloneListener;
    }

    public void setAloneListener(boolean aloneListener) {
        this.aloneListener = aloneListener;
    }
}
