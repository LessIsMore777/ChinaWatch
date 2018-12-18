package com.waterworld.watch.login.manager;

import android.content.Context;
import android.util.Log;

import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.dao.LoginDao;
import com.waterworld.watch.common.interfaces.ILoginDao;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.NetManager;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.bean.LoginResponse;
import com.waterworld.watch.login.interfaces.ILoginManager;
import com.waterworld.watch.common.net.BaseResultBean;

import com.waterworld.watch.login.interfaces.LoginResultListener;


import rx.Subscriber;

/**
 * Created by nhuan
 * Time:2018/12/13.
 * Login manager
 */

public class LoginManager implements ILoginManager{
    private static LoginManager mInstance;
    private Context mContext;
    private ILoginDao iLoginDao;
    /**
     * get application single instance
     */
    public static LoginManager getInstance(){
        if(mInstance == null){
            synchronized (LoginManager.class){
                if(mInstance == null){
                    mInstance = new LoginManager(MyApplication.getContext());
                }
            }
        }
        return mInstance;
    }

    public LoginManager(Context context){
        super();
        this.mContext = context;
        iLoginDao = new LoginDao();
    }

    @Override
    public void getAuthCode(String number, final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().getAuthCode(number, new Subscriber<BaseResultBean>() {
            @Override
            public void onNext(BaseResultBean baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }

            @Override
            public void onError(Throwable t) {
                baseObserverListener.onError(t);
            }

            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }
        });
    }

    @Override
    public void register(String number, String pwd, BaseObserverListener baseObserverListener) {

    }



    @Override
    public Object getLastLogin() {
        return null;
    }

    @Override
    public void login(String number, String pwd, String type ,final LoginResultListener loginResultListener) {
        NetManager.getInstance().login(number, pwd, type,new Subscriber<LoginResponse>() {
            @Override
            public void onNext(LoginResponse loginResponse) {
                if(loginResponse.getCode() == 0){
                    //LoginBean loginBean = loginResponse.getData();
                    //LoginBean.getInstance().setUsername(loginBean.getUsername());
                    //LoginBean.getInstance().setPassword(loginBean.getPassword());
                    Log.d("nihuan","登录成功");
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                loginResultListener.onError(t);
                Log.d("nihaun","失败");
            }

            @Override
            public void onCompleted() {

            }
        });
    }
}
