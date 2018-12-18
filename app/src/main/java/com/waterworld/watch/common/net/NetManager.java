package com.waterworld.watch.common.net;

import com.waterworld.watch.login.bean.BindBean;
import com.waterworld.watch.login.bean.LoginResponse;
import com.waterworld.watch.login.bean.RegisterResponse;



import java.util.HashMap;
import java.util.Map;

import rx.Observer;
import rx.Subscriber;


/**
 * Created by nhuan
 * Time:2018/12/14.
 */

public class NetManager implements INetManager {

    private static NetManager mInstance;

    public static NetManager getInstance() {
        if (mInstance == null) {
            synchronized (NetManager.class) {
                if (mInstance == null) {
                    mInstance = new NetManager();
                }
            }
        }
        return mInstance;
    }
    @Override
    public void getAuthCode(String number, Subscriber<BaseResultBean> subscriber) {
        RetrofitClient.getInstance().getApiService().getAuthCode(UrlContants.GET_AUTH_CODE,number)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe((Observer) subscriber);
    }

    @Override
    public void verifyLogin(String number, String code, Subscriber<BaseResultBean> subscriber) {

    }

    @Override
    public void updatepwd(String newPwd, String oldPwd, Subscriber<BaseResultBean> subscriber) {
        Map<String,String> map = new HashMap<>();
        map.put("newPwd",newPwd);
        map.put("oldPwd",oldPwd);
        RetrofitClient.getInstance().getApiService().updatepwd("8C6166A80546C6E57DD492B339B4FAE9",UrlContants.MODIFY_PASSWORD,map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    @Override
    public void register(String number, String code, String pwd, Subscriber<RegisterResponse> subscriber) {
        Map<String,String> map = new HashMap<>();
        map.put("phone",number);
        map.put("pwd",pwd);
        RetrofitClient.getInstance().getApiService().Register(UrlContants.REGISTER,map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    @Override
    public void login(String phone, String pwd, String type ,Subscriber<LoginResponse> subscriber) {
        Map<String,String> map = new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",pwd);
        map.put("type",type);
        RetrofitClient.getInstance().getApiService().login(UrlContants.LOGIN,map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe((Observer) subscriber);
    }

    @Override
    public void bind(BindBean bindBean, Subscriber<BaseResultBean> subscriber) {

    }

    @Override
    public void unbind(String token, String id, String imei, Subscriber<BaseResultBean> subscriber) {

    }
}
