package com.waterworld.watch.home.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.adapter.SkillContactListAdapter;
import com.waterworld.watch.home.bean.SkillContactBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/12/12 18:48
 * 主要作用：联系人列表(活动)
 */
public class SkillContactListActivity extends BaseActivity implements View.OnClickListener {

    private SkillContactListActivity thisActivity;

    /**
     * 头部组件
     */
    private LinearLayout header_parent;
    private ImageView header_back;
    private TextView header_title;
    private ImageButton header_add;
    /**
     * Recycler
     */
    private RecyclerView contactRecycler;
    private List<SkillContactBean> contactData;
    private SkillContactListAdapter contactAdapter;
    /**
     * body
     */
    private CheckBox switchBlack;//黑名单开关
    private Button btnSubmit;//提交按钮
    private ImageButton btnTip;//帮助按钮
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;
        setContentView(R.layout.activity_skill_contact);
        bindView();
        initView();
        initRecycler();
        bindClick();
        RecyclerClick();
    }

    private void bindView() {
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
        header_add = findViewById(R.id.header_add);
        contactRecycler = findViewById(R.id.rv_contact_recycler);
        switchBlack = findViewById(R.id.cb_blackList);
        btnSubmit = findViewById(R.id.btn_submit);
    }

    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 12);
        header_title.setText("手表联系人");
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_add.setVisibility(View.VISIBLE);
    }

    private void initRecycler() {
        contactData = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            contactData.add(new SkillContactBean(getResources().getDrawable(R.drawable.ic_avatar_1),
                    "爸爸" + i, "17763693759", true, true, true));
        }
        contactAdapter = new SkillContactListAdapter(thisActivity, contactData);
        contactRecycler.setLayoutManager(new LinearLayoutManager(thisActivity, LinearLayoutManager.VERTICAL, false));
        contactRecycler.setAdapter(contactAdapter);
    }

    private void bindClick(){
        header_back.setOnClickListener(thisActivity);
        header_add.setOnClickListener(thisActivity);
        btnSubmit.setOnClickListener(thisActivity);
        switchBlack.setOnClickListener(thisActivity);
    }

    private void RecyclerClick(){
        contactAdapter.setOnContactClickListener(new SkillContactListAdapter.onContactClickListener() {
            @Override
            public void contactClick(View view, int position) {
                Bundle bundle = new Bundle();
                bundle.putInt("editContact",position);
                goActivity(SkillContactInfoActivity.class,bundle);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.header_back:
                finish();
                break;
            case R.id.header_add://增加联系人
                Bundle bundle = new Bundle();
                bundle.putString("addContact","add");
                goActivity(SkillContactInfoActivity.class,bundle);
                break;
            case R.id.btn_submit:
                ToastUtils.showCustomTime(thisActivity,"提交成功",3000);
                break;
            case R.id.cb_blackList:
                if (switchBlack.isChecked()){
                    ToastUtils.showCustomTime(thisActivity,"打开",3000);
                } else {
                    ToastUtils.showCustomTime(thisActivity,"关闭",3000);
                }
                break;
        }
    }
}
