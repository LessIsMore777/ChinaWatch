package com.waterworld.watch.login.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.net.NetworkUtils;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.login.manager.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeActivity extends BaseActivity {

    private static final int INTENT_PAGER = 0x01;
    private static final int AUTO_PAGER = 0x02;
    private static final int DURATION = 2 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ButterKnife.bind(this);
        mHandler.sendEmptyMessageDelayed(INTENT_PAGER,DURATION);
        isCheckAutoLogin();
    }


    private void isCheckAutoLogin() {
        if(LoginManager.getInstance().isCheckAutoLogin()){
            mHandler.removeMessages(INTENT_PAGER);
            mHandler.sendEmptyMessageDelayed(AUTO_PAGER,DURATION);
        }
    }

    private void autoLogin() {
        if(NetworkUtils.isNetworkAvailable(this)) {
            startActivity(new Intent(WelcomeActivity.this, HomePagerActivity.class));
            MyApplication.getInstance().getService().autoLogin();
            finish();
        }else {
            ToastUtils.showShort(this,getString(R.string.check_net_is_error));
            startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
        }
    }

    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case INTENT_PAGER:
                    startActivity(new Intent(WelcomeActivity.this,LoginActivity.class));
                    finish();
                    break;
                case AUTO_PAGER:
                    autoLogin();
                    break;
                default:
                    break;
            }
        }
    };


}
