package com.waterworld.watch.mine.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.LoginUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.interfaces.CodeCountListener;
import com.waterworld.watch.login.interfaces.ILoginManager;
import com.waterworld.watch.login.manager.LoginManager;
import com.waterworld.watch.mine.event.ModifyPhoneEvent;
import com.waterworld.watch.mine.interfaces.IMineManager;
import com.waterworld.watch.mine.interfaces.ModifyAccountResultListener;
import com.waterworld.watch.mine.manager.MineManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 编写者：Created by SunnyTang
 * 时间：2019/1/10 13:49
 * 主要作用：
 */
public class ModifyAccountActivity extends BaseActivity {

    @BindView(R.id.header_parent)
    LinearLayout headerParent;

    @BindView(R.id.header_back)
    ImageButton headerBack;

    @BindView(R.id.header_title)
    TextView headerTitle;

    @BindView(R.id.header_confirm)
    Button headerConfirm;

    @BindView(R.id.et_new_phoneNumber)
    EditText etPhoneNumber;

    @BindView(R.id.et_authCode)
    EditText etAuthCode;

    @BindView(R.id.et_password)
    EditText etPassword;

    @BindView(R.id.rl_getAuthCode)
    RelativeLayout rlAuthCode;

    @BindView(R.id.tv_authCode)
    TextView tvAuthCode;

    @BindView(R.id.pb_authCode)
    ProgressBar pbAuthCode;


    IMineManager iMineManager = MineManager.getInstance();
    ILoginManager iLoginManager = LoginManager.getInstance();

    private final int AUTH_CODE_SEND = 0x00;        //发送验证码
    private final int AUTH_CODE_SEND_SUCCESS = 0x01;//验证码发送成功
    private final int AUTH_CODE_SEND_FAIL = 0x02;   //验证码发送失败
    private final int MODIFY_ACCOUNT_SUCCESS = 0x03;//修改手机号成功
    private final int MODIFY_ACCOUNT_FAIL = 0x04;   //修改手机号失败
    private final int MODIFY_ACCOUNT_ERROR = 0x05;  //网络错误

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case AUTH_CODE_SEND:
                    viewTextColorChange(true, false);
                    getAuthCode();
                    break;
                case AUTH_CODE_SEND_SUCCESS:
                    viewTextColorChange(false, false);

                    break;
                case AUTH_CODE_SEND_FAIL:
                    viewTextColorChange(false, true);
                    rlAuthCode.setEnabled(true);
                    break;
                case MODIFY_ACCOUNT_SUCCESS:
                    closeDialog();
                    ToastUtils.showShort(ModifyAccountActivity.this, getString(R.string.modify_account_success));
                    //存储到本地数据库
                    String username = LoginBean.getInstance().getUsername();
                    LoginBean loginBean = LoginBean.getInstance();
                    loginBean.setUsername(etPhoneNumber.getText().toString());
                    LoginManager.getInstance().update(username,loginBean);

                    ModifyPhoneEvent modifyPhoneEvent = new ModifyPhoneEvent(etPhoneNumber.getText().toString());
                    EventBus.getDefault().post(modifyPhoneEvent);
                    finish();
                    break;
                case MODIFY_ACCOUNT_FAIL:
                    closeDialog();
                    break;
                case MODIFY_ACCOUNT_ERROR:
                    closeDialog();
                    ToastUtils.showShort(ModifyAccountActivity.this, getString(R.string.check_net_is_error));
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_account);
        ButterKnife.bind(this);
        initView();
        doClick();
        codeCountListeners();
    }


    private void initView() {
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText(getString(R.string.modify_account));
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
        headerConfirm.setVisibility(View.VISIBLE);
    }

    private void doClick() {
        headerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rlAuthCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.isPhone(ModifyAccountActivity.this, etPhoneNumber.getText().toString().trim())) {
                    mHandler.sendEmptyMessage(AUTH_CODE_SEND);
                }
            }
        });
        headerConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (LoginUtils.isPhone(ModifyAccountActivity.this, etPhoneNumber.getText().toString().trim()) &&
                        LoginUtils.isCode(ModifyAccountActivity.this, etAuthCode.getText().toString().trim()) &&
                        LoginUtils.isPassword(ModifyAccountActivity.this, etPassword.getText().toString().trim())) {
                    showDialog(getString(R.string.modifying),null,null);
                    iMineManager.modifyPhone(etPhoneNumber.getText().toString().trim(), etAuthCode.getText().toString().trim(), etPassword.getText().toString().trim(), new ModifyAccountResultListener<BaseResultBean>() {
                        @Override
                        public void onSuccess(BaseResultBean baseResultBean) {
                            mHandler.sendEmptyMessage(MODIFY_ACCOUNT_SUCCESS);
                        }

                        @Override
                        public void onFail(BaseResultBean baseResultBean) {
                            mHandler.sendEmptyMessage(MODIFY_ACCOUNT_FAIL);
                            ToastUtils.showShort(ModifyAccountActivity.this,getString(R.string.modify_account_fail)+","+baseResultBean.getMsg());
                        }

                        @Override
                        public void onError(BaseResultBean baseResultBean) {
                            mHandler.sendEmptyMessage(MODIFY_ACCOUNT_ERROR);
                        }
                    });
                }
            }
        });
    }

    private void getAuthCode() {
        if (iLoginManager != null) {
            iLoginManager.getAuthCode(etPhoneNumber.getText().toString().trim(), "3", new BaseObserverListener() {
                @Override
                public void onCompleted() {
                    mHandler.sendEmptyMessage(AUTH_CODE_SEND_SUCCESS);
                }

                @Override
                public void onError(Throwable e) {
                    e.printStackTrace();
                    mHandler.sendEmptyMessage(AUTH_CODE_SEND_FAIL);
                    ToastUtils.showShort(ModifyAccountActivity.this, getString(R.string.data_error));
                }

                @Override
                public void onNext(Object o) {

                }
            });
        }
    }

    private void viewTextColorChange(boolean isCode, boolean isError) {
        if (isCode) {
            rlAuthCode.setEnabled(false);
            tvAuthCode.setVisibility(View.GONE);
            pbAuthCode.setVisibility(View.VISIBLE);
        } else {
            pbAuthCode.setVisibility(View.GONE);
            tvAuthCode.setVisibility(View.VISIBLE);
            if (MyApplication.getInstance().getService() != null && !isError) {
                MyApplication.getInstance().getService().startCodeCountDown();
            }
        }
    }

    private void codeCountListeners() {
        if (MyApplication.getInstance().getService() != null) {
            //发送成功后等待60s才可以重新发送
            MyApplication.getInstance().getService().setVerificationDown(new CodeCountListener() {
                @Override
                public void count(int count) {
                    rlAuthCode.setEnabled(false);
                    tvAuthCode.setText(getString(R.string.verification_re_send) + "(" + count + ")");
                }

                @Override
                public void complete() {
                    rlAuthCode.setEnabled(true);
                    tvAuthCode.setText(R.string.get_verification_code);
                }
            });
        }
    }
}
