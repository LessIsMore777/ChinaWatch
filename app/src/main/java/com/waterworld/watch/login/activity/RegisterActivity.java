package com.waterworld.watch.login.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.LoginUtils;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.interfaces.CodeCountListener;
import com.waterworld.watch.login.interfaces.ILoginManager;
import com.waterworld.watch.login.manager.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 注册页
 */
public class RegisterActivity extends BaseActivity implements View.OnClickListener {
    //用户
    @BindView(R.id.et_register_user)
    EditText register_user;
    //清除用户
    @BindView(R.id.iv_register_clear_user)
    ImageView register_clear_user;
    //输入密码
    @BindView(R.id.et_register_input_password)
    EditText register_input_password;
    //隐藏密码
    @BindView(R.id.iv_register_hide_input_password)
    ImageView register_hide_input_password;
    //确认密码
    @BindView(R.id.et_register_confirm_password)
    EditText register_confirm_password;
    //隐藏确认密码
    @BindView(R.id.iv_register_hide_confirm_password)
    ImageView register_hide_confirm_password;
    //输入验证码
    @BindView(R.id.et_input_verification)
    EditText input_verification;
    //获取验证码
    @BindView(R.id.tv_get_verification)
    TextView get_verification;
    //注册
    @BindView(R.id.btn_register)
    Button register;
    //已经有账号，登录
    @BindView(R.id.tv_login)
    TextView login;
    //ProgressBar
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ILoginManager iLoginManager = LoginManager.getInstance();
    private final static String TAG = RegisterActivity.class.getSimpleName();

    //密码是否显示(默认不显示)
    private boolean inputPwdIsShow = false;
    //确认密码是否显示(默认不显示)
    private boolean confirmPwdIsShow = false;

    private static final int SEND_VERIFICATION = 0X01;  //请求获取验证码
    private static final int SEND_SUCCESS_VERIFICATION = 0X02;  //发送成功
    private static final int REGISTER_SUCCESS = 0X03;  //注册成功
    private static final int SEND_FAIL = 0x04; //发送失败


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
                case REGISTER_SUCCESS:
                    registerSuccess();
                    break;
                case SEND_FAIL:
                    viewTextColorChange(false,true);
                    get_verification.setEnabled(true);
                    get_verification.setTextColor(Color.parseColor("#FED533"));
                    break;
                default:
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        bindOnClick();
        codeCountListeners();
    }

    private void bindOnClick(){
        register_clear_user.setOnClickListener(this);
        register_hide_input_password.setOnClickListener(this);
        register_hide_confirm_password.setOnClickListener(this);
        get_verification.setOnClickListener(this);
        register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_register_clear_user:
                register_user.setText("");
                break;
            case R.id.iv_register_hide_input_password:
                register_hide_input_password.setImageResource(inputPwdIsShow ? R.drawable.login_hide_password : R.drawable.login_show_password);
                register_input_password.setInputType(inputPwdIsShow ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                inputPwdIsShow = !inputPwdIsShow;
                break;
            case R.id.iv_register_hide_confirm_password:
                register_hide_confirm_password.setImageResource(confirmPwdIsShow ? R.drawable.login_hide_password : R.drawable.login_show_password);
                register_confirm_password.setInputType(confirmPwdIsShow ? InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD : InputType.TYPE_CLASS_TEXT);
                confirmPwdIsShow = !confirmPwdIsShow;
                break;
            case R.id.tv_get_verification:
                if (LoginUtils.isPhone(RegisterActivity.this,register_user.getText().toString())){
                    mHandler.sendEmptyMessage(SEND_VERIFICATION);
                }
                break;
            case R.id.btn_register:
                if (LoginUtils.isPhone(RegisterActivity.this,register_user.getText().toString()) &&
                        LoginUtils.isPassword(RegisterActivity.this,register_input_password.getText().toString()) &&
                        LoginUtils.isSamePassword(RegisterActivity.this,register_input_password.getText().toString(),register_confirm_password.getText().toString()) &&
                        LoginUtils.isCode(RegisterActivity.this,input_verification.getText().toString())){
                    showDialog(getString(R.string.registering),null,null);
                    register();
                }
                break;
            case R.id.tv_login:
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                finish();
                break;
            default:
                break;
        }
    }

    /**
     * 获取验证码
     */
    private void getAuthCode(){
        if(iLoginManager != null) {
            iLoginManager.getAuthCode(register_user.getText().toString(), "1", new BaseObserverListener<BaseResultBean<String>>() {
                @Override
                public void onCompleted() {
                    mHandler.sendEmptyMessage(SEND_SUCCESS_VERIFICATION);
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                    ToastUtils.showShort(RegisterActivity.this,getString(R.string.data_error));
                    mHandler.sendEmptyMessage(SEND_FAIL);
                }
                @Override
                public void onNext(BaseResultBean<String> baseResultBean) {
                    //短信验证码
                    //String authCode = baseResultBean.getData();
                    //ToastUtils.showShort(RegisterActivity.this,baseResultBean.getMsg());
                }
            });
        }
    }
    /**
     * 注册
     */
    private void register(){
        if (iLoginManager != null){
            iLoginManager.register(register_user.getText().toString(),
                    register_input_password.getText().toString(),
                    input_verification.getText().toString(), new BaseObserverListener<BaseResultBean>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onError(Throwable e) {
                    closeDialog();
                    ToastUtils.showShort(RegisterActivity.this,getString(R.string.data_error));
                }

                @Override
                public void onNext(BaseResultBean baseResultBean) {
                    closeDialog();
                    if(baseResultBean != null){
                        if(baseResultBean.getCode() == 0){
                            Log.d(TAG,"register code = "+baseResultBean.getCode()+", msg = "+ baseResultBean.getMsg());
                            LoginBean loginBean = LoginBean.getInstance();
                            loginBean.setUsername(register_user.getText().toString());
                            loginBean.setPassword(register_input_password.getText().toString());
                            LoginManager.getInstance().insertDB(loginBean);
                            mHandler.sendEmptyMessage(REGISTER_SUCCESS);
                        }else {
                            ToastUtils.showShort(RegisterActivity.this,getString(R.string.register_fail)+","+baseResultBean.getMsg());
                        }
                    }
                }
            });
        }
    }

    /**
     * 注册成功之后
     */
    private void registerSuccess(){
        ToastUtils.showShort(RegisterActivity.this,getString(R.string.register_success));
        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
        finish();
    }

    /**
     * UI变化
     * @param isCode 是否发送
     * @param isError 是否错误
     */
    private void viewTextColorChange(boolean isCode,boolean isError){
        if(isCode){
            get_verification.setEnabled(false);
            get_verification.setTextColor(Color.parseColor("#979797"));
            get_verification.setVisibility(View.GONE);
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
        if(MyApplication.getInstance().getService() != null){
            //发送成功后等待60s才可以重新发送
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
                    get_verification.setText(R.string.get_verification_code);
                    get_verification.setTextColor(Color.parseColor("#FED533"));
                }
            });
        }
    }

}
