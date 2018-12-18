package com.waterworld.watch.common.net;

import com.waterworld.watch.login.bean.BindBean;
import com.waterworld.watch.login.bean.LoginResponse;
import com.waterworld.watch.login.bean.RegisterResponse;

import rx.Subscriber;


/**
 * Created by nhuan
 * Time:2018/12/14.
 */

public interface INetManager {
    /**
     * 获取短信验证码
     */
    void getAuthCode(String number, Subscriber<BaseResultBean> subscriber);

    /**
     * 验证码登录
     */
    void verifyLogin(String number,String code, Subscriber<BaseResultBean> subscriber);

    /**
     * 修改密码
     */
    void updatepwd(String newPwd,String oldPwd,Subscriber<BaseResultBean> subscriber);

    /**
     * 注册
     */
    void register(String number,String code,String pwd,Subscriber<RegisterResponse> subscriber);

    /**
     * 登录
     */
    void login(String phone,String pwd,String type,Subscriber<LoginResponse> subscriber);

    /**
     * 绑定
     */
    void bind(BindBean bindBean, Subscriber<BaseResultBean> subscriber);

    /**
     * 解绑
     */
    void unbind(String token,String id,String imei,Subscriber<BaseResultBean> subscriber);
}
