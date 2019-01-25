package com.waterworld.watch.home.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/5 18:26
 * 主要作用：解绑手表(活动)
 */
public class WatchUnbindActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout header_parent;
    private ImageButton header_back;
    private TextView header_title;
    private RelativeLayout unbind;
    private RelativeLayout shiftManager;
    private RelativeLayout unbindOtherUser;
    private RelativeLayout factorySetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_unbind);
        bindView();
        initView();
        bindClick();
    }

    private void bindView() {
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
        unbind = findViewById(R.id.rl_unbind);
        shiftManager = findViewById(R.id.rl_shift_manager);
        unbindOtherUser = findViewById(R.id.rl_unbind_other_user);
        factorySetting = findViewById(R.id.rl_factory_setting);
    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        header_title.setText("解除绑定");
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
    }

    private void bindClick() {
        header_back.setOnClickListener(this);
        unbind.setOnClickListener(this);
        shiftManager.setOnClickListener(this);
        unbindOtherUser.setOnClickListener(this);
        factorySetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.rl_unbind:
                unbindClick("确定解绑？","取消","确定");
                break;
            case R.id.rl_shift_manager:

                break;
            case R.id.rl_unbind_other_user:
                goActivity(WatchUnbindOtherUserActivity.class);
                break;
            case R.id.rl_factory_setting:
                unbindClick("确定恢复出厂设置？","取消","确定");
                break;
        }
    }

    private void unbindClick(String content,String btnExitText,String btnEnterText){
//        showNormalDialog(content, btnExitText, btnEnterText, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                ToastUtils.showCustomTime(WatchUnbindActivity.this, "解绑成功", 3000);
//            }
//        }, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                ToastUtils.showCustomTime(WatchUnbindActivity.this, "解绑成功", 3000);
//            }
//        });
        showNormalDialog("解绑", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}
