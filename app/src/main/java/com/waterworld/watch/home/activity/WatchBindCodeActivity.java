package com.waterworld.watch.home.activity;

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
 * 时间：2018/12/5 17:40
 * 主要作用：绑定码页
 */
public class WatchBindCodeActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout header_parent;
    private ImageButton header_back;
    private TextView header_title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_bind_code);
        bindView();
        initView();
        bindClick();
    }

    private void bindView() {
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 11);
        header_title.setText("绑定码");
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
    }

    private void bindClick() {
        header_back.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
        }
    }
}
