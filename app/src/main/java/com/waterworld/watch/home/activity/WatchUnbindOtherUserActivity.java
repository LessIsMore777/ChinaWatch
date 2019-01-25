package com.waterworld.watch.home.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.adapter.WatchOtherUserAdapter;
import com.waterworld.watch.home.bean.WatchOtherUserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/6 10:07
 * 主要作用：解除与其他用户的绑定页(活动)
 */
public class WatchUnbindOtherUserActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout header_parent;
    private ImageButton header_back;
    private TextView header_title;
    private RecyclerView otherUserRecycler;
    private List<WatchOtherUserBean> otherUserList;
    private WatchOtherUserAdapter otherUserAdapter;
    private LinearLayoutManager layoutManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_unbind_other_user);
        bindView();
        initView();
        initRecycler();
        bindClick();
        unbindClick();
    }

    private void bindView() {
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
        otherUserRecycler = findViewById(R.id.rv_other_user);
        layoutManager = new LinearLayoutManager(this);
    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        header_title.setText("解除其它用户的绑定");
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
    }

    private void initRecycler() {
        otherUserList = new ArrayList<>();
        otherUserList.add(new WatchOtherUserBean(1, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserList.add(new WatchOtherUserBean(2, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserList.add(new WatchOtherUserBean(3, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserList.add(new WatchOtherUserBean(4, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserList.add(new WatchOtherUserBean(5, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserList.add(new WatchOtherUserBean(6, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserList.add(new WatchOtherUserBean(7, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserList.add(new WatchOtherUserBean(8, getResources().getDrawable(R.drawable.ic_avatar_1), "唐家辉", "哥哥"));
        otherUserAdapter = new WatchOtherUserAdapter(this, otherUserList);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        otherUserRecycler.setLayoutManager(layoutManager);
        otherUserRecycler.setAdapter(otherUserAdapter);
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

    private void unbindClick() {
        otherUserAdapter.setOnOtherUserClick(new WatchOtherUserAdapter.OtherUserClickListener() {
            @Override
            public void onOtherUserClick(View view, int position) {
                unbindOtherUser(position);
            }
        });
    }

    private void unbindOtherUser(int position) {
        showNormalDialog("确定解绑？", "取消", "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtils.showCustomTime(WatchUnbindOtherUserActivity.this, "解绑成功", 3000);
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ToastUtils.showCustomTime(WatchUnbindOtherUserActivity.this, "解绑成功", 3000);
            }
        });
    }

}
