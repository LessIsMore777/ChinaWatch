package com.waterworld.watch.login.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.bean.BindWatchBean;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.common.util.AppManager;
import com.waterworld.watch.common.util.LoginUtils;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.activity.WatchBindActivity;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.event.AutoLoginEvent;
import com.waterworld.watch.login.interfaces.ILoginManager;
import com.waterworld.watch.login.interfaces.LoginResultListener;
import com.waterworld.watch.login.manager.LoginManager;

import org.greenrobot.eventbus.EventBus;

import crossoverone.statuslib.StatusUtil;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener{

    //用户
    private EditText user;
    //清除用户
    private ImageView clear_user;
    //密码
    private EditText password;
    //隐藏密码
    private ImageView hide_password;
    //忘记密码
    private TextView forget_password;
    //验证码登录
    private TextView verification_login;
    //登录
    private Button login;
    //注册
    private Button register;

    private static final int LOGIN_SUCCESS_TYPE = 0X01;
    private static final int LOGIN_FAIL_TYPE = 0X02;
    private static final int LOGIN_NET_ERROR = 0x03;

    //密码是否显示(默认不显示)
    private boolean inputPwdIsShow = false;
    private ILoginManager iLoginManager = LoginManager.getInstance();
    private IHomeManager iHomeManager = HomeManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        bindClick();
        setDefault();
    }

    private void bindView(){
        user = findViewById(R.id.et_login_user);
        clear_user = findViewById(R.id.iv_login_clear_user);
        password = findViewById(R.id.et_login_password);
        hide_password = findViewById(R.id.iv_login_hide_password);
        forget_password = findViewById(R.id.tv_forget_password);
        verification_login = findViewById(R.id.tv_verification_login);
        login = findViewById(R.id.btn_login);
        register = findViewById(R.id.btn_register);
    }

    private void bindClick() {
        user.setOnClickListener(this);
        clear_user.setOnClickListener(this);
        password.setOnClickListener(this);
        hide_password.setOnClickListener(this);
        forget_password.setOnClickListener(this);
        verification_login.setOnClickListener(this);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void setDefault(){
        if (iLoginManager.getLastLogin() != null){
            LoginBean loginBean = (LoginBean) iLoginManager.getLastLogin();
            user.setText(loginBean.getUsername());
            password.setText(loginBean.getPassword());
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_login_clear_user:
                user.setText("");
                break;
            case R.id.iv_login_hide_password:
                hide_password.setImageResource(inputPwdIsShow ? R.drawable.login_hide_password : R.drawable.login_show_password);
                password.setInputType(inputPwdIsShow ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                inputPwdIsShow = !inputPwdIsShow;
                break;
            case R.id.tv_forget_password:
                startActivity(new Intent(this,ForgetPasswordActivity.class));
                finish();
                break;
            case R.id.tv_verification_login:
                startActivity(new Intent(this,VerifyLoginActivity.class));
                finish();
                break;
            case R.id.btn_login:
                if(LoginUtils.isPhone(this,user.getText().toString()) &&
                        LoginUtils.isPassword(this,password.getText().toString())) {
                    showDialog(getString(R.string.logining), null, null);
                    login();
                }
                break;
            case R.id.btn_register:
                startActivity(new Intent(this,RegisterActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case LOGIN_SUCCESS_TYPE:
                    loginSuccess();

                    break;
                case LOGIN_FAIL_TYPE:
                    loginFail();
                    break;
                case LOGIN_NET_ERROR:
                    loginNetError();
                default:
                    break;
            }
        }
    };

    private void loginSuccess(){
        if (iLoginManager != null){
            iLoginManager.insertDB(LoginBean.getInstance());
        }
        //ToastUtils.showShort(this,getString(R.string.login_success));
        if (iHomeManager != null) {
            iHomeManager.listBindWatch(new BaseObserverListener<BaseResultBean<BindWatchBean[]>>() {
                @Override
                public void onCompleted() {

                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShort(LoginActivity.this, getString(R.string.data_error));
                }

                @Override
                public void onNext(BaseResultBean<BindWatchBean[]> baseResultBean) {
                    BindWatchBean[] bindWatchs = baseResultBean.getData();
                    AutoLoginEvent autoLoginEvent = new AutoLoginEvent();
                    autoLoginEvent.setAuto(1);
                    EventBus.getDefault().postSticky(autoLoginEvent);
                    if (bindWatchs.length > 0) {
                        Intent intent = new Intent(LoginActivity.this, HomePagerActivity.class);
                        startActivity(intent);
                    }else {
                        Intent intent = new Intent(LoginActivity.this, WatchBindActivity.class);
                        startActivity(intent);
                    }
                    closeDialog();
                    AppManager.finishAllActivity();
                    finish();
                }
            });
        }

    }

    private void loginFail(){
        closeDialog();
        ToastUtils.showShort(this,getString(R.string.login_fail));
    }

    private void loginNetError(){
        closeDialog();
        ToastUtils.showShort(this,getString(R.string.check_net_is_error));
    }

    private void login(){
        if (iLoginManager != null) {
            iLoginManager.login(user.getText().toString(), "0", password.getText().toString(), new LoginResultListener() {
                @Override
                public void onSuccess(Object o) {
                    LoginBean.getInstance().setUsername(user.getText().toString());
                    LoginBean.getInstance().setPassword(password.getText().toString());
                    mHandler.sendEmptyMessage(LOGIN_SUCCESS_TYPE);
                }

                @Override
                public void onFail(Object o) {
                    //服务器原因
                    mHandler.sendEmptyMessage(LOGIN_FAIL_TYPE);
                }

                @Override
                public void onError(Object o) {
                    //网络原因
                    mHandler.sendEmptyMessage(LOGIN_NET_ERROR);
                }
            });
        }
    }
}
