package com.waterworld.watch.login.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.util.AppManager;
import com.waterworld.watch.common.util.LoginUtils;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.login.interfaces.CodeCountListener;
import com.waterworld.watch.login.interfaces.ILoginManager;
import com.waterworld.watch.login.interfaces.LoginResultListener;
import com.waterworld.watch.login.manager.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 验证码登录页
 */
public class VerifyLoginActivity extends BaseActivity implements View.OnClickListener {

    //用户
    @BindView(R.id.et_verify_login_user)
    EditText verify_login_user;
    //清除用户
    @BindView(R.id.iv_verify_login_clear_user)
    ImageView verify_login_clear_user;
    //输入验证码
    @BindView(R.id.et_input_verification)
    EditText input_verification;
    //获取验证码
    @BindView(R.id.tv_get_verification)
    TextView get_verification;
    //验证码登录
    @BindView(R.id.btn_login)
    Button btn_login;
    //已经有账号，登录
    @BindView(R.id.tv_login)
    TextView tv_login;
    //progressBar
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private static final int SEND_VERIFICATION = 0x01;          //发送验证码
    private static final int SEND_SUCCESS_VERIFICATION = 0x02;  //发送验证码成功
    private static final int LOGIN_SUCCESS = 0x03;              //登录成功
    private static final int SEND_FAIL = 0x04;                  //发送验证码失败
    private static final int LOGIN_FAIL = 0x05;                 //登录失败

    private ILoginManager iLoginManager = LoginManager.getInstance();
    @SuppressLint("HandlerLeak")
    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case SEND_VERIFICATION:
                    viewTextColorChange(true,false);
                    getAuthCode();
                    break;
                case SEND_SUCCESS_VERIFICATION:
                    viewTextColorChange(false,false);
                    break;
                case LOGIN_SUCCESS:
                    loginSuccess();
                    break;
                case SEND_FAIL:
                    viewTextColorChange(false,true);
                    get_verification.setVisibility(View.VISIBLE);
                    get_verification.setTextColor(Color.parseColor("#FED533"));
                    break;
                case LOGIN_FAIL:
                    loginFail();
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_login);
        ButterKnife.bind(this);
        bindClick();
        codeCountListeners();
    }

    private void bindClick(){
        verify_login_clear_user.setOnClickListener(this);
        get_verification.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_verify_login_clear_user:
                verify_login_user.setText("");
                break;
            case R.id.tv_get_verification:
                if(LoginUtils.isPhone(this,verify_login_user.getText().toString())){
                    mHandler.sendEmptyMessage(SEND_VERIFICATION);
                }
                break;
            case R.id.btn_login:
                if(LoginUtils.isPhone(this,verify_login_user.getText().toString()) &&
                        LoginUtils.isCode(this,input_verification.getText().toString())){
                    showDialog(getString(R.string.logining),null,null);
                    login();
                }
                break;
            case R.id.tv_login:
                startActivity(new Intent(VerifyLoginActivity.this,LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    private void login(){
        if (iLoginManager != null){
            iLoginManager.login(verify_login_user.getText().toString(), "1",
                    input_verification.getText().toString(), new LoginResultListener() {
                @Override
                public void onSuccess(Object o) {
                    mHandler.sendEmptyMessage(LOGIN_SUCCESS);
                }

                @Override
                public void onFail(Object o) {
                    mHandler.sendEmptyMessage(LOGIN_FAIL);
                }

                @Override
                public void onError(Object o) {
                    mHandler.sendEmptyMessage(LOGIN_FAIL);
                }
            });
        }
    }

    private void loginSuccess(){
        closeDialog();
        ToastUtils.showShort(this,getString(R.string.login_success));
        Intent intent = new Intent(this, HomePagerActivity.class);
        startActivity(intent);
        AppManager.finishAllActivity();
        finish();
    }

    private void loginFail(){
        closeDialog();
        ToastUtils.showShort(this,getString(R.string.login_fail));
    }
    private void getAuthCode(){
        if(iLoginManager != null){
            iLoginManager.getAuthCode(verify_login_user.getText().toString(), "0", new BaseObserverListener<BaseResultBean>() {
                @Override
                public void onCompleted() {
                    mHandler.sendEmptyMessage(SEND_SUCCESS_VERIFICATION);
                }

                @Override
                public void onError(Throwable e) {
                    ToastUtils.showShort(VerifyLoginActivity.this,getString(R.string.data_error));
                    mHandler.sendEmptyMessage(SEND_FAIL);
                }

                @Override
                public void onNext(BaseResultBean baseResultBean) {
                    //短信验证码
                    //String authCode = baseResultBean.getData();
                    //ToastUtils.showShort(RegisterActivity.this,baseResultBean.getMsg());
                }
            });
        }
    }

    private void viewTextColorChange(Boolean isCode,Boolean isError){
        if(isCode){
            get_verification.setEnabled(false);
            get_verification.setVisibility(View.GONE);
            get_verification.setTextColor(Color.parseColor("#979797"));
            progressBar.setVisibility(View.VISIBLE);
        }else {
            progressBar.setVisibility(View.GONE);
            get_verification.setVisibility(View.VISIBLE);
            if(MyApplication.getInstance().getService() != null && !isError){
                MyApplication.getInstance().getService().startCodeCountDown();
            }
        }
    }

    private void codeCountListeners(){
        MyApplication.getInstance().getService().setVerificationDown(new CodeCountListener() {
            @Override
            public void count(int count) {
                get_verification.setEnabled(false);
                get_verification.setTextColor(Color.parseColor("#979797"));
                get_verification.setText(getString(R.string.verification_re_send)+"("+count+")");
            }

            @Override
            public void complete() {
                get_verification.setEnabled(true);
                get_verification.setTextColor(Color.parseColor("#FED533"));
                get_verification.setText(R.string.get_verification_code);
            }
        });
    }
}
