package com.waterworld.watch.login.interfaces;


import com.waterworld.watch.common.net.BaseObserverListener;
/**
 * Created by nhuan
 * Time:2018/12/13.
 */

public interface ILoginManager<T> {
    /**
     * 获取短信验证码
     */
    void getAuthCode(String number, BaseObserverListener baseObserverListener);

    /**
     * 注册
     */
    void register(String number,String pwd,BaseObserverListener baseObserverListener);

    /**
     * 登录
     */
    void login(String number,String pwd,String type ,LoginResultListener loginResultListener);

    /**
     * 获取最后一次登录信息
     */
    T getLastLogin();
}
