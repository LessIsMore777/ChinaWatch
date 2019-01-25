package com.waterworld.watch.login.manager;

import android.content.Context;
import android.util.Log;

import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.dao.LoginDao;
import com.waterworld.watch.common.interfaces.ILoginDao;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.NetManager;
import com.waterworld.watch.login.bean.LoginBean;
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

    private LoginManager(Context context){
        super();
        this.mContext = context;
        iLoginDao = new LoginDao();
    }


    @Override
    public void getAuthCode(String phone,String type ,final BaseObserverListener baseObserverListener){
        NetManager.getInstance().getAuthCode(phone, type, new Subscriber<BaseResultBean<String>>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable t) {
                baseObserverListener.onError(t);
            }

            @Override
            public void onNext(BaseResultBean<String> baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    @Override
    public void register(String phone, String pwd, String authCode, final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().register(phone, pwd,authCode, new Subscriber<BaseResultBean>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    @Override
    public void login(String phone, String type, String pwd ,final LoginResultListener loginResultListener) {
        NetManager.getInstance().login(phone, type, pwd, new Subscriber<BaseResultBean<String>>() {
            @Override
            public void onNext(BaseResultBean<String> baseResultBean) {
                if(baseResultBean.getCode() == 0){
                    //获取sessionID
                    String sessionID = baseResultBean.getData();
                    MyApplication.getInstance().setSessionID(sessionID);
                    loginResultListener.onSuccess(baseResultBean);
                }else {
                    loginResultListener.onFail(baseResultBean);
                }
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
                loginResultListener.onError(t);
            }

            @Override
            public void onCompleted() {

            }
        });
    }



    @Override
    public void forgetPwd(String phone, String pwd, String authCode, final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().forgetPwd(phone, pwd, authCode, new Subscriber<BaseResultBean>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    @Override
    public Object getLastLogin() {
        if (iLoginDao != null){
            return iLoginDao.getNewsLogin();
        }
        return null;
    }

    @Override
    public void logout(LoginResultListener loginResultListener) {

    }

    @Override
    public boolean insertDB(Object o) {
        if(iLoginDao != null){
            iLoginDao.insert(o);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(String username, Object o) {
        if(iLoginDao != null){
            iLoginDao.upDate(username,o);
            return true;
        }
        return false;
    }

    @Override
    public boolean isCheckAutoLogin() {
        LoginBean loginBean = (LoginBean) getLastLogin();
        if (loginBean != null){
            if (loginBean.getPassword() != null && !loginBean.getPassword().equals("")){
                return true;
            }
        }
        return false;
    }
}
