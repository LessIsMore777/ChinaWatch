package com.waterworld.watch.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;

public class VerifyLoginActivity extends BaseActivity implements View.OnClickListener {

    //用户
    private EditText verify_login_user;
    //清除用户
    private ImageView verify_login_clear_user;
    //输入验证码
    private EditText input_verification;
    //获取验证码
    private TextView get_verification;
    //验证码登录
    private Button btn_login;
    //已经有账号，登录
    private TextView tv_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_login);
        bindView();
        bindClick();
    }

    private void bindView(){
        verify_login_user = findViewById(R.id.et_verify_login_user);
        verify_login_clear_user = findViewById(R.id.iv_verify_login_clear_user);
        input_verification = findViewById(R.id.et_input_verification);
        get_verification = findViewById(R.id.tv_get_verification);
        btn_login = findViewById(R.id.btn_login);
        tv_login = findViewById(R.id.tv_login);
    }

    private void bindClick(){
        verify_login_user.setOnClickListener(this);
        verify_login_clear_user.setOnClickListener(this);
        input_verification.setOnClickListener(this);
        get_verification.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_login.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_verify_login_user:
                break;
            case R.id.iv_verify_login_clear_user:
                break;
            case R.id.et_input_verification:
                break;
            case R.id.tv_get_verification:
                break;
            case R.id.btn_login:
                break;
            case R.id.tv_login:
                break;
            default:
                break;
        }
    }
}
