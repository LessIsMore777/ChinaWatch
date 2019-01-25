package com.waterworld.watch.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
 * 时间：2019/1/9 11:16
 * 主要作用：
 */
public class MessageDetailedInfoActivity extends BaseActivity {

    private LinearLayout headerParent; //根布局
    private ImageButton headerBack; //返回按钮
    private TextView headerTitle; //标题

    private TextView tvContent;
    private TextView tvTime;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detailed_info);
        bindView();
        initView();
        doClick();
    }

    private void bindView(){
        headerParent = findViewById(R.id.header_parent);
        headerBack = findViewById(R.id.header_back);
        headerTitle = findViewById(R.id.header_title);
        tvTime = findViewById(R.id.tv_time);
        tvContent = findViewById(R.id.tv_content);
    }

    private void initView(){
        String time = getIntent().getStringExtra("time");
        String content = getIntent().getStringExtra("content");
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText("消息通知");
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
        tvTime.setText(time);
        tvContent.setText(content);
    }

    private void doClick(){
        headerBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
