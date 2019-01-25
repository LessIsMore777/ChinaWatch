package com.waterworld.watch.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/5 15:11
 * 主要作用：手表信息(活动)
 */
public class WatchInfoActivity extends BaseActivity implements View.OnClickListener {
    /**
     * 头部组件
     */
    private LinearLayout header_parent;
    private ImageButton header_back;
    private TextView header_title;

    /**
     * 头部信息
     */
    private ConstraintLayout babyInfo;//宝贝信息
    private ConstraintLayout watchBindCode;//绑定码
    private TextView watchType;//手表型号
    private TextView watchVersion;//固件版本号
    private ConstraintLayout watchBind;//绑定手表
    private ConstraintLayout watchUnbind;//解绑手表
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_info);
        bindView();
        initView();
        bindClick();
    }

    private void bindView(){
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);

        babyInfo = findViewById(R.id.cl_baby_info);
        watchBindCode = findViewById(R.id.cl_watch_bind_code);
        watchType = findViewById(R.id.tv_watch_type_info);
        watchVersion = findViewById(R.id.tv_watch_version_info);
        watchBind = findViewById(R.id.cl_watch_bind);
        watchUnbind = findViewById(R.id.cl_watch_unbind);
    }
    private void initView(){
        setViewSize(header_parent,ViewGroup.LayoutParams.MATCH_PARENT,ScreenAdapterUtil.getHeightPx(this) / 12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_title.setText("手表信息");
        watchType.setText("Q20TC");
        watchVersion.setText("Q20TC_TY_V04");
    }

    private void bindClick(){
        header_back.setOnClickListener(this);
        babyInfo.setOnClickListener(this);
        watchBindCode.setOnClickListener(this);
        watchBind.setOnClickListener(this);
        watchUnbind.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.header_back:
                finish();
                break;
            case R.id.cl_baby_info:
                goActivity(BabyInfoActivity.class);
                break;
            case R.id.cl_watch_bind_code:
                goActivity(WatchBindCodeActivity.class);
                break;
            case R.id.cl_watch_bind:
                goActivity(WatchBindActivity.class);
                break;
            case R.id.cl_watch_unbind:
                goActivity(WatchUnbindActivity.class);
                break;
        }
    }

}
