package com.waterworld.watch.login.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.interfaces.ILoginManager;
import com.waterworld.watch.login.interfaces.LoginResultListener;
import com.waterworld.watch.login.manager.LoginManager;

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

    private ILoginManager iLoginManager = LoginManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindView();
        bindClick();
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

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.et_login_user:
                break;
            case R.id.iv_login_clear_user:
                break;
            case R.id.et_login_password:
                break;
            case R.id.iv_login_hide_password:
                break;
            case R.id.tv_forget_password:
                break;
            case R.id.tv_verification_login:
                break;
            case R.id.btn_login:
                if(iLoginManager != null){
                    iLoginManager.login(user.getText().toString(),password.getText().toString(),"0",new LoginResultListener() {
                        @Override
                        public void onSuccess(Object o) {
                            LoginBean.getInstance().setUsername(user.getText().toString());
                            LoginBean.getInstance().setPassword(password.getText().toString());
                            Log.d("nihuan","成功");
                        }

                        @Override
                        public void onFail(Object o) {
                            Log.d("nihuan","失败");
                        }

                        @Override
                        public void onError(Object o) {
                        }
                    });
                }
                break;
            case R.id.btn_register:
                break;
            default:
                break;
        }
    }
}
