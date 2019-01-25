package com.waterworld.watch.home.activity;

import android.os.Bundle;
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
 * 时间：2018/12/17 15:39
 * 主要作用：单项聆听(活动)
 */
public class SkillSingleListenerActivity extends BaseActivity implements View.OnClickListener {

    private SkillSingleListenerActivity thisActivity;
    /**
     * 头部组件
     */
    private LinearLayout headerParent;
    private TextView headerTitle;
    private ImageButton headerBack;

    private Button btnListener;//开始聆听按钮
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_single_listener);
        bindView();
        initView();
        bindClick();
    }

    private void bindView() {
        headerParent = findViewById(R.id.header_parent);
        headerTitle = findViewById(R.id.header_title);
        headerBack = findViewById(R.id.header_back);
        btnListener = findViewById(R.id.btn_listener);
    }

    private void initView() {
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText("聆听人");
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
    }

    private void bindClick() {
        headerBack.setOnClickListener(thisActivity);
        btnListener.setOnClickListener(thisActivity);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.btn_listener:
                break;
        }
    }
}
