package com.waterworld.watch.mine.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.customview.CommonPopupWindow;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.mine.adapter.MessageAdapter;
import com.waterworld.watch.mine.bean.MessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2019/1/5 15:12
 * 主要作用：消息通知(活动)
 */
public class MessageNotificationActivity extends BaseActivity implements View.OnClickListener {

    private MessageNotificationActivity thisActivity = this;

    private LinearLayout headerParent; //根布局
    private ImageButton headerBack; //返回按钮
    private TextView headerTitle; //标题
    private ImageButton headerMenu;//菜单按钮

    private RecyclerView mRecycler;
    private List<MessageBean> mList;
    private MessageAdapter mAdapter;

    /**
     * 泡泡窗口
     */
    private CommonPopupWindow popupWindow; //自定义通用泡泡窗口
    private CommonPopupWindow.LayoutGravity layoutGravity;// 泡泡窗口锚点对象

    /**
     *
     */
    private ConstraintLayout cl_delete;
    private ConstraintLayout cl_read;
    private TextView tv_read;
    private TextView tv_delete;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mine_message_notification);
        bindView();
        setView();
        initRecycler();
        bindClick();
        doClick();
        initPopupWindow();
    }

    private void bindView() {
        headerParent = findViewById(R.id.header_parent);
        headerBack = findViewById(R.id.header_back);
        headerTitle = findViewById(R.id.header_title);
        headerMenu = findViewById(R.id.header_menu);
        mRecycler = findViewById(R.id.rv_message);
    }

    private void setView() {
        setViewSize(headerParent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        headerTitle.setText("消息通知");
        headerBack.setVisibility(View.VISIBLE);
        headerTitle.setVisibility(View.VISIBLE);
        headerMenu.setVisibility(View.VISIBLE);
    }

    private void bindClick() {
        headerBack.setOnClickListener(thisActivity);
        headerMenu.setOnClickListener(thisActivity);
    }

    private void initRecycler() {
        mList = new ArrayList<>();
        mList.add(new MessageBean("手表的电量低请及时充电", "2018-12-31 19:16:18", true));
        mList.add(new MessageBean("手表离开安全区请注意", "2018-12-31 19:16:18", true));
        mList.add(new MessageBean("手表进入安全区请注意", "2018-12-31 19:16:18", true));
        mAdapter = new MessageAdapter(thisActivity, mList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(thisActivity);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(layoutManager);
        mRecycler.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_menu:
                popupWindow.showBashOfAnchor(headerMenu, layoutGravity, 0, 0);
                if (mList == null || mList.size() <= 0){
                    cl_read.setEnabled(false);
                    cl_delete.setEnabled(false);
                    tv_read.setTextColor(getResources().getColor(R.color.gray_3));
                    tv_delete.setTextColor(getResources().getColor(R.color.gray_3));
                } else {
                    cl_read.setEnabled(true);
                    cl_delete.setEnabled(true);
                    tv_read.setTextColor(getResources().getColor(R.color.black_1));
                    tv_delete.setTextColor(getResources().getColor(R.color.black_1));
                }
                break;
        }
    }



    private void initPopupWindow() {
        popupWindow = new CommonPopupWindow(thisActivity, R.layout.popup_message, ScreenAdapterUtil.getWidthPx(thisActivity) / 2, ViewGroup.LayoutParams.WRAP_CONTENT) {
            @Override
            protected void initView() {
                View view = getContentView();
                cl_delete = view.findViewById(R.id.cl_delete);
                cl_read = view.findViewById(R.id.cl_read);
                tv_read = view.findViewById(R.id.tv_read);
                tv_delete = view.findViewById(R.id.tv_delete);
            }

            @Override
            protected void initEvent() {
                cl_delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mList.clear();
                        mAdapter.notifyDataSetChanged();
                        //ToastUtils.showShort(thisActivity,"全部删除");
                        popupWindow.getPopupWindow().dismiss();
                    }
                });
                cl_read.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        for (int i = 0; i <= mList.size() - 1; i++) {
                            mList.get(i).setRead(false);
                        }
                        mAdapter.notifyDataSetChanged();
                        //ToastUtils.showShort(thisActivity, "全部已读");
                        popupWindow.getPopupWindow().dismiss();
                    }
                });
            }
        };
        layoutGravity = new CommonPopupWindow.LayoutGravity(CommonPopupWindow.LayoutGravity.CENTER_HORIZONTAL | CommonPopupWindow.LayoutGravity.TO_BOTTOM);
    }

    private void doClick() {
        mAdapter.setOnItemClick(new MessageAdapter.onItemClick() {
            @Override
            public void ItemClick(View view, int position) {
                if (mList.get(position).isRead()) {
                    mList.get(position).setRead(false);
                }
                String time = mList.get(position).getTime();
                String content = mList.get(position).getContent();
                Bundle bundle = new Bundle();
                bundle.putString("time", time);
                bundle.putString("content", content);
                thisActivity.goActivity(MessageDetailedInfoActivity.class, bundle);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        if (mList == null || mList.size() <= 0){
            cl_read.setEnabled(false);
            cl_delete.setEnabled(false);
            tv_read.setTextColor(getResources().getColor(R.color.gray_3));
            tv_delete.setTextColor(getResources().getColor(R.color.gray_3));
        } else {
            cl_read.setEnabled(true);
            cl_delete.setEnabled(true);
            tv_read.setTextColor(getResources().getColor(R.color.black_1));
            tv_delete.setTextColor(getResources().getColor(R.color.black_1));
        }
    }
}
