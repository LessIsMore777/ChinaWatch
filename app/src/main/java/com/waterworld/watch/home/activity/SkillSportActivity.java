package com.waterworld.watch.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
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
 * 时间：2019/1/11 11:01
 * 主要作用：
 */
public class SkillSportActivity extends BaseActivity implements View.OnClickListener {

    private SkillSportActivity thisActivity;

    private LinearLayout headerParent;
    private ImageButton headerBack;
    private TextView headerTitle;

    private TextView tvWalkCount;//显示步数
    private TextView tvTime;//数据更新的时间
    private Button btnRefresh;//刷新按钮
    private TextView tvCalorie;//显示卡路里
    private TextView tvMeleage;//显示里程
    private ConstraintLayout clLocus;//运动轨迹

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skill_sport);
        thisActivity = this;
        bindView();
        initView();
        bindClick();
    }

    private void bindView(){
        headerParent = findViewById(R.id.header_parent);
        headerBack = findViewById(R.id.header_back);
        headerTitle = findViewById(R.id.header_title);

        tvWalkCount = findViewById(R.id.tv_walk_count);
        tvTime = findViewById(R.id.tv_time);
        btnRefresh = findViewById(R.id.btn_Refresh);
        tvCalorie = findViewById(R.id.tv_calorie);
        tvMeleage = findViewById(R.id.tv_mileage);
        clLocus = findViewById(R.id.cl_locus);
    }

    private void initView(){
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText("头像");
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
    }

    private void bindClick(){
        btnRefresh.setOnClickListener(this);
        clLocus.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.header_back:
                finish();
                break;
            case R.id.btn_Refresh:
                break;
            case R.id.cl_locus:
                break;
        }
    }
}
