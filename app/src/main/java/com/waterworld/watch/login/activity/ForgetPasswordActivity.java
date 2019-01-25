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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.AppManager;
import com.waterworld.watch.common.util.LoginUtils;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.interfaces.CodeCountListener;
import com.waterworld.watch.login.interfaces.ILoginManager;
import com.waterworld.watch.login.interfaces.LoginResultListener;
import com.waterworld.watch.login.manager.LoginManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 忘记密码页
 */
public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

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
    //登录
    @BindView(R.id.btn_login)
    Button btn_login;
    //已经有账号，登录
    @BindView(R.id.tv_login)
    TextView tv_login;
    //ProgressBar
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    //密码是否显示(默认不显示)
    private boolean inputPwdIsShow = false;
    //确认密码是否显示(默认不显示)
    private boolean confirmPwdIsShow = false;

    private ILoginManager iLoginManager = LoginManager.getInstance();
    private final static String TAG = ForgetPasswordActivity.class.getSimpleName();

    private static final int SEND_VERIFICATION = 0X01;          //请求获取验证码
    private static final int SEND_SUCCESS_VERIFICATION = 0X02;  //发送成功
    private static final int LOGIN_SUCCESS = 0X03;              //注册成功
    private static final int SEND_FAIL = 0x04;                  //发送失败
    private static final int LOGIN_FAIL = 0x05;                 //登录失败

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
                    get_verification.setEnabled(true);
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
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        bindClick();
        codeCountListeners();
    }

    private void bindClick(){
        register_clear_user.setOnClickListener(this);
        register_hide_input_password.setOnClickListener(this);
        register_hide_confirm_password.setOnClickListener(this);
        get_verification.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        tv_login.setOnClickListener(this);
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
                if (LoginUtils.isPhone(ForgetPasswordActivity.this,register_user.getText().toString())){
                    mHandler.sendEmptyMessage(SEND_VERIFICATION);
                }
                break;
            case R.id.btn_login:
                if (LoginUtils.isPhone(ForgetPasswordActivity.this,register_user.getText().toString()) &&
                        LoginUtils.isPassword(ForgetPasswordActivity.this,register_input_password.getText().toString()) &&
                        LoginUtils.isSamePassword(ForgetPasswordActivity.this,register_input_password.getText().toString(),register_confirm_password.getText().toString()) &&
                        LoginUtils.isCode(ForgetPasswordActivity.this,input_verification.getText().toString())){
                    showDialog(getString(R.string.logining),null,null);
                    login();
                }
                break;
            case R.id.tv_login:
                startActivity(new Intent(this,LoginActivity.class));
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
            iLoginManager.getAuthCode(register_user.getText().toString(), "0", new BaseObserverListener<BaseResultBean<String>>() {
                @Override
                public void onCompleted() {
                    mHandler.sendEmptyMessage(SEND_SUCCESS_VERIFICATION);
                }

                @Override
                public void onError(Throwable t) {
                    t.printStackTrace();
                    ToastUtils.showShort(ForgetPasswordActivity.this,getString(R.string.data_error));
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

    private void loginSuccess(){
        if (iLoginManager != null){
            iLoginManager.insertDB(LoginBean.getInstance());
        }
        closeDialog();
        ToastUtils.showShort(this,getString(R.string.login_success));
        Intent intent = new Intent(ForgetPasswordActivity.this, HomePagerActivity.class);
        startActivity(intent);
        AppManager.finishAllActivity();
        finish();
    }

    private void loginFail(){
        closeDialog();
        ToastUtils.showShort(this,getString(R.string.login_fail));
    }

    private void login(){
        if (iLoginManager != null) {
            iLoginManager.forgetPwd(register_user.getText().toString(), register_input_password.getText().toString(),input_verification.getText().toString(), new BaseObserverListener<BaseResultBean>() {
                @Override
                public void onCompleted() {
                }

                @Override
                public void onNext(BaseResultBean baseResultBean) {
                    if(baseResultBean.getCode() == 0) {
                        LoginBean.getInstance().setUsername(register_input_password.getText().toString());
                        LoginBean.getInstance().setPassword(register_input_password.getText().toString());
                        mHandler.sendEmptyMessage(LOGIN_SUCCESS);
                    }else {
                        ToastUtils.showShort(ForgetPasswordActivity.this,getString(R.string.login_fail)+","+baseResultBean.getMsg());
                    }
                }

                @Override
                public void onError(Throwable e) {
                    mHandler.sendEmptyMessage(LOGIN_FAIL);
                }
            });
        }
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
