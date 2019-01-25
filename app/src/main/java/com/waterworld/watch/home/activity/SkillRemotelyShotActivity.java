package com.waterworld.watch.home.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/17 15:28
 * 主要作用：远程拍摄(活动)
 */
public class SkillRemotelyShotActivity extends BaseActivity implements View.OnClickListener {

    private SkillRemotelyShotActivity thisActivity;
    /**
     * 头部组件
     */
    private LinearLayout headerParent;
    private TextView headerTitle;
    private ImageButton headerBack;

    private Button btnShot;//开始拍摄按钮

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_skill_shot);
        bindView();
        initView();
        bindClick();
    }

    private void bindView() {
        headerParent = findViewById(R.id.header_parent);
        headerTitle = findViewById(R.id.header_title);
        headerBack = findViewById(R.id.header_back);
        btnShot = findViewById(R.id.btn_shot);
    }

    private void initView() {
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText("远程拍摄");
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
    }

    private void bindClick() {
        headerBack.setOnClickListener(thisActivity);
        btnShot.setOnClickListener(thisActivity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.btn_shot:
                btnShot.setText("拍摄中...");
                mHandler.sendEmptyMessageDelayed(0x01,1500);
                break;
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0x01:
                    btnShot.setText("远程拍摄");
                    break;
            }
        }
    };
}
