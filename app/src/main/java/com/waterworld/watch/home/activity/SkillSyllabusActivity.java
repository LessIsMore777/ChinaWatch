package com.waterworld.watch.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.LinearLayout;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/17 14:08
 * 主要作用：课程表主页(活动)
 */
public class SkillSyllabusActivity extends BaseActivity {

    private LinearLayout parentView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aaaaa);
        parentView = findViewById(R.id.parentView);
    }

    private void initView(){

    }
}
