package com.waterworld.watch.common.application;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.sdk.android.push.CloudPushService;
import com.alibaba.sdk.android.push.CommonCallback;
import com.alibaba.sdk.android.push.noonesdk.PushServiceFactory;
import com.waterworld.watch.common.service.OverallService;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/27 14:26
 * 主要作用：该类的作用，用于在应用程序启动的时候，就初始化一些必要的参数。
 * 比如本程序的SDK。在这里面，给SDK设置秘钥之类的必要的参数，
 * 那么之后不管在哪里使用，都不用设置秘钥了。
 * 还有就是dialog，在这里面设置好dialog的风格，以后使用的时候，
 * 就不用在单独设置风格了
 */
public class MyApplication extends Application {

    private static MyApplication instance = null;
    private static Context mContext;
    private OverallService mService = null;
    private static final String TAG = MyApplication.class.getSimpleName();
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
        bindService();   //绑定全局服务
        //initCloudChannel(this);
    }

    private void initCloudChannel(Context applicationContext) {
        PushServiceFactory.init(applicationContext);
        CloudPushService pushService = PushServiceFactory.getCloudPushService();
        pushService.register(applicationContext, new CommonCallback() {
            @Override
            public void onSuccess(String response) {
                Log.d(TAG, "init cloudchannel success");
            }
            @Override
            public void onFailed(String errorCode, String errorMessage) {
                Log.d(TAG, "init cloudchannel failed -- errorcode:" + errorCode + " -- errorMessage:" + errorMessage);
            }
        });
    }

    /**
     * 得到Application的实例
     */
    public static MyApplication getInstance(){
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }

    private void bindService(){
        Intent intent = new Intent(this, OverallService.class);
        bindService(intent,conn,BIND_AUTO_CREATE);
    }

    public OverallService getService() {
        return mService;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            OverallService.LocalBinder binder = (OverallService.LocalBinder) service;
            mService = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    private String SessionID = "";

    public void setSessionID(String SessionID){
        this.SessionID = SessionID;
    }

    public String getSessionID() {
        return SessionID;
    }
}
