package com.waterworld.watch.mine.activity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.customview.CircleImageView;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.UrlContants;
import com.waterworld.watch.common.util.PhotoUtils;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.mine.event.AvatarEvent;
import com.waterworld.watch.mine.event.ModifyPhoneEvent;
import com.waterworld.watch.mine.interfaces.IMineManager;
import com.waterworld.watch.mine.manager.MineManager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyInformationActivity extends BaseActivity {

    private static final String TAG = MyInformationActivity.class.getSimpleName();

    @BindView(R.id.rl_avatar)
    RelativeLayout rl_avatar;

    @BindView(R.id.iv_avatar)
    CircleImageView iv_avatar;

    @BindView(R.id.et_nickname)
    EditText et_nickname;

    @BindView(R.id.et_relationship)
    EditText et_relationship;

    @BindView(R.id.rl_modify_account)
    RelativeLayout rl_modify_account;

    @BindView(R.id.rl_modify_password)
    RelativeLayout rl_modify_password;

    @BindView(R.id.header_back)
    ImageButton header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_confirm)
    TextView header_save;

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.tv_account)
    TextView tv_account;

    private IMineManager iMineManager = MineManager.getInstance();

    private String imgName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }

    private void initView() {
        //设置标题栏
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setText(R.string.my_information);
        header_title.setVisibility(View.VISIBLE);
        header_save.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.rl_avatar)
    void onAvatar() {
        startActivity(new Intent(this, AvatarActivity.class));
    }

    @OnClick(R.id.rl_modify_account)
    void onModifyAccount() {
        startActivity(new Intent(this,ModifyAccountActivity.class));
    }

    @OnClick(R.id.rl_modify_password)
    void onModifyPassword() {
        startActivity(new Intent(this, ModifyPasswordActivity.class));
    }

    @OnClick(R.id.header_back)
    void back(){
        finish();
    }

    @OnClick(R.id.header_confirm)
    void save() {
        showDialog(getString(R.string.uploading),null,null);
        modifyUserInfo();
    }


    private void modifyUserInfo() {
        if(iMineManager != null){
            iMineManager.modifyUserInfo(imgName, et_nickname.getText().toString(), et_relationship.getText().toString(), new BaseObserverListener() {
                @Override
                public void onCompleted() {
                    closeDialog();
                    ToastUtils.showShort(MyInformationActivity.this,getString(R.string.modify_info_success));
                }

                @Override
                public void onError(Throwable e) {
                    closeDialog();
                    ToastUtils.showShort(MyInformationActivity.this,getString(R.string.modify_info_fail));
                }

                @Override
                public void onNext(Object o) {

                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    //EventBus设置头像数据
    @Subscribe(sticky = true,threadMode = ThreadMode.MAIN)
    public void setAvatar(AvatarEvent avatar){
        if(avatar.getType() == 0){
            //自定义头像,在拍照完后上传至服务器，再从服务器获取图片uri,便于统一操作
            //图片路径baseUrl/resources/watch/+head
            imgName = avatar.getName();
            Glide.with(this).load(UrlContants.BASE_URL+"resources/watch/"+imgName).into(iv_avatar);

        }else if (avatar.getType() == 1){
            //系统头像
            imgName = avatar.getName();
            Glide.with(this).load(PhotoUtils.getResource(imgName.substring(0,13))).into(iv_avatar);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void setNewPhoneNumber(ModifyPhoneEvent modifyPhoneEvent){
        tv_account.setText(modifyPhoneEvent.getPhone());
    }
}
