package com.waterworld.watch.login.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;

public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    //用户
    private EditText register_user;
    //清除用户
    private ImageView register_clear_user;
    //输入密码
    private EditText register_input_password;
    //隐藏密码
    private ImageView register_hide_input_password;
    //确认密码
    private EditText register_confirm_password;
    //隐藏确认密码
    private ImageView register_hide_confirm_password;
    //输入验证码
    private EditText input_verification;
    //获取验证码
    private TextView get_verification;
    //注册
    private Button register;
    //已经有账号，登录
    private TextView login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        bindView();
        bindClick();
    }

    private void bindView(){
        register_user = findViewById(R.id.et_register_user);
        register_clear_user = findViewById(R.id.iv_register_clear_user);
        register_input_password = findViewById(R.id.et_register_input_password);
        register_hide_input_password = findViewById(R.id.iv_register_hide_input_password);
        register_confirm_password = findViewById(R.id.et_register_confirm_password);
        register_hide_confirm_password = findViewById(R.id.iv_register_hide_confirm_password);
        input_verification = findViewById(R.id.et_input_verification);
        get_verification = findViewById(R.id.tv_get_verification);
        register = findViewById(R.id.btn_register);
        login = findViewById(R.id.tv_login);
    }

    private void bindClick(){
        register_user.setOnClickListener(this);
        register_clear_user.setOnClickListener(this);
        register_input_password.setOnClickListener(this);
        register_hide_input_password.setOnClickListener(this);
        register_confirm_password.setOnClickListener(this);
        register_hide_confirm_password.setOnClickListener(this);
        input_verification.setOnClickListener(this);
        get_verification.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_register_user:
                break;
            case R.id.iv_register_clear_user:
                break;
            case R.id.et_register_input_password:
                break;
            case R.id.iv_register_hide_input_password:
                break;
            case R.id.et_register_confirm_password:
                break;
            case R.id.iv_register_hide_confirm_password:
                break;
            case R.id.et_input_verification:
                break;
            case R.id.tv_get_verification:
                break;
            case R.id.btn_register:
                break;
            case R.id.tv_login:
                break;
            default:
                break;
        }
    }
}
