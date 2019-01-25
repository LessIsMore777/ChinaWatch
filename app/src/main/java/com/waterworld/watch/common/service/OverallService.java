package com.waterworld.watch.common.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.waterworld.watch.R;
import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.customview.dialog.LoadingDialog;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.AppManager;
import com.waterworld.watch.common.util.DialogUtils;
import com.waterworld.watch.login.activity.LoginActivity;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.event.AutoLoginEvent;
import com.waterworld.watch.login.interfaces.CodeCountListener;
import com.waterworld.watch.login.interfaces.LoginResultListener;
import com.waterworld.watch.login.manager.LoginManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by nhuan
 * Time:2018/12/20.
 * 全局后台服务
 */

public class OverallService extends Service {

    private final static String TAG = OverallService.class.getSimpleName();

    //短信倒计时回调
    private CodeCountListener codeCountListener;
    //短信重新发送时间
    private int mCodeCount = 60;
    //通过eventbus实时发送自动连接状态
    private AutoLoginEvent autoLoginEvent = null;
    //短信验证码倒计时
    private final static int VERIFICATION_CODE_COUNT_TYPE = 0x01;

    public class LocalBinder extends Binder{
        public OverallService getService(){
            return OverallService.this;
        }
    }

    private LocalBinder binder = new LocalBinder();

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case VERIFICATION_CODE_COUNT_TYPE:
                    if(mCodeCount>0){
                        mCodeCount--;
                        codeCountListener.count(mCodeCount);
                        mHandler.sendEmptyMessageDelayed(VERIFICATION_CODE_COUNT_TYPE,1000);
                    }else {
                        codeCountListener.complete();
                        mCodeCount = 60;
                    }
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void setVerificationDown(CodeCountListener codeCountListener){
        this.codeCountListener = codeCountListener;
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void loginStatus(AutoLoginEvent autoLoginEvent){
        if(autoLoginEvent.getAuto() == 2) {
            DialogUtils.LoginInvalidShowDialog(MyApplication.getContext(),
                    getString(R.string.sweet_toast), autoLoginEvent.getMessage(), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(AppManager.currentActivity(), LoginActivity.class);
                            intent.putExtra("userLoginInvalid", true);
                            startActivity(intent);
                            AppManager.finishAllActivity();
                        }
                    });
        }
    }
    /**
     * 短信倒计时
     */
    public void startCodeCountDown(){
        mHandler.sendEmptyMessage(VERIFICATION_CODE_COUNT_TYPE);
    }

    /**
     * 后台自动登录
     */
    public void autoLogin(){
        if(autoLoginEvent == null){
            autoLoginEvent = new AutoLoginEvent();
        }
        LoginBean loginBean = (LoginBean) LoginManager.getInstance().getLastLogin();
        LoginManager.getInstance().login(loginBean.getUsername(), "0", loginBean.getPassword(), new LoginResultListener() {
            @Override
            public void onSuccess(Object o) {
                BaseResultBean<String> baseResultBean = (BaseResultBean<String>) o;
                //MyApplication.getInstance().setSessionID(baseResultBean.getData());
                autoLoginEvent.setAuto(1);
                autoLoginEvent.setMessage(baseResultBean.getMsg());
                sendEvent(autoLoginEvent);
            }

            @Override
            public void onFail(Object o) {
                BaseResultBean<String> baseResultBean = (BaseResultBean<String>) o;
                autoLoginEvent.setAuto(2);
                autoLoginEvent.setMessage(baseResultBean.getMsg());
                sendEvent(autoLoginEvent);
            }

            @Override
            public void onError(Object o) {
                autoLoginEvent.setAuto(2);
                sendEvent(autoLoginEvent);
            }
        });
    }

    private void sendEvent(Object o){
        if (o != null){
            EventBus.getDefault().post(o);
        }
    }
}
