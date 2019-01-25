package com.waterworld.watch.login.interfaces;


import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;

import rx.Subscriber;

/**
 * Created by nhuan
 * Time:2018/12/13.
 */

public interface ILoginManager<T> {
    /**
     * 获取短信验证码
     */
    void getAuthCode(String phone,String type, BaseObserverListener baseObserverListener);

    /**
     * 注册
     */
    void register(String phone,String pwd,String authCode,BaseObserverListener baseObserverListener);

    /**
     * 登录
     */
    void login(String phone,String type ,String pwd,LoginResultListener loginResultListener);

    /**
     * 忘记密码
     */
    void forgetPwd(String phone, String pwd, String authCode, BaseObserverListener baseObserverListener);

    /**
     * 退出登录
     */
    void logout(LoginResultListener loginResultListener);
    /**
     * 插入数据库
     */
    boolean insertDB(T t);

    /**
     * 更新数据库
     */
    boolean update(String username,T t);

    /**
     * 获取最后一次登录信息
     */
    T getLastLogin();

    /**
     * 检查是否需要后台自动登录
     */
    boolean isCheckAutoLogin();


}
