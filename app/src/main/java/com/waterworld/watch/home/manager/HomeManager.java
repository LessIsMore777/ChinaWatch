package com.waterworld.watch.home.manager;

import android.content.Context;

import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.dao.LoginDao;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.net.NetManager;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.login.manager.LoginManager;

import rx.Subscriber;

/**
 * Created by nhuan
 * Time:2019/1/17.
 */

public class HomeManager implements IHomeManager {

    private static HomeManager mInstance;
    private Context mContext;

    public static HomeManager getInstance(){
        if(mInstance == null){
            synchronized (LoginManager.class){
                if(mInstance == null){
                    mInstance = new HomeManager(MyApplication.getContext());
                }
            }
        }
        return mInstance;
    }

    private HomeManager(Context context){
        super();
        this.mContext = context;
    }

    /**
     * 绑定手表
     * @param imei 设备IMEI号
     * @param baseObserverListener
     */
    @Override
    public void bindWatch(String imei, BaseObserverListener baseObserverListener) {
        NetManager.getInstance().bindWatch(imei, new Subscriber<BaseResultBean>() {
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

    /**
     * 编辑主页功能区
     * @param functionSigns 功能标识集。
     * @param baseObserverListener
     */
    @Override
    public void editHomePageFunction(String functionSigns, BaseObserverListener baseObserverListener) {
        NetManager.getInstance().editHomePagerFunction(functionSigns, new Subscriber<BaseResultBean>() {
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

    /**
     * 获取所有功能
     * @param baseObserverListener
     */
    @Override
    public void getAllFunction(final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().getAllFunction(new Subscriber<BaseResultBean>() {
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

    /**
     * 获取未开启的功能
     * @param baseObserverListener
     */
    @Override
    public void getNotUseFunction(final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().getNotUseFunction(new Subscriber<BaseResultBean>() {
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
}
