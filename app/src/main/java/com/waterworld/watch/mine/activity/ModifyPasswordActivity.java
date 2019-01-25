package com.waterworld.watch.mine.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.DialogUtils;
import com.waterworld.watch.common.util.LoginUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.login.activity.ForgetPasswordActivity;
import com.waterworld.watch.login.bean.LoginBean;
import com.waterworld.watch.login.manager.LoginManager;
import com.waterworld.watch.mine.event.ModifyPhoneEvent;
import com.waterworld.watch.mine.interfaces.IMineManager;
import com.waterworld.watch.mine.manager.MineManager;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ModifyPasswordActivity extends BaseActivity {

    @BindView(R.id.header_parent)
    LinearLayout headerParent;

    @BindView(R.id.header_back)
    ImageButton header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_confirm)
    Button header_confirm;

    @BindView(R.id.ed_oldPwd)
    EditText ed_oldPwd;

    @BindView(R.id.ed_newPwd)
    EditText ed_newPwd;

    private IMineManager iMineManager = MineManager.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_password);
        ButterKnife.bind(this);
        initView();
    }

    //设置标题栏
    public void initView(){
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setText(R.string.modify_password);
        header_title.setVisibility(View.VISIBLE);
        header_confirm.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.header_confirm)
    void confirm(){
        if (LoginUtils.isPassword(this,ed_oldPwd.getText().toString()) && LoginUtils.isPassword(this,ed_newPwd.getText().toString())){
            showDialog(getString(R.string.modifying),null,null);
            //修改密码
            if(iMineManager != null){
                iMineManager.modifyPwd(ed_newPwd.getText().toString(), ed_oldPwd.getText().toString(), new BaseObserverListener<BaseResultBean>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable t) {
                        closeDialog();
                        ToastUtils.showShort(ModifyPasswordActivity.this, getString(R.string.check_net_is_error));
                    }

                    @Override
                    public void onNext(BaseResultBean baseResultBean) {
                        closeDialog();
                        if (baseResultBean.getCode() == 0){
                            //修改成功
                            ToastUtils.showShort(ModifyPasswordActivity.this,getString(R.string.modify_password_success));
                            String username = LoginBean.getInstance().getUsername();
                            LoginBean loginBean = LoginBean.getInstance();
                            loginBean.setPassword(ed_newPwd.getText().toString());
                            LoginManager.getInstance().update(username,loginBean);
                            finish();
                        }else {
                            ToastUtils.showShort(ModifyPasswordActivity.this,getString(R.string.modify_password_fail)+","+baseResultBean.getMsg());
                        }
                    }
                });
            }
        }
    }

    @OnClick(R.id.header_back)
    void back(){
        finish();
    }
}
