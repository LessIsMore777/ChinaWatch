package com.waterworld.watch.home.fragment;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.common.fragment.BaseFragment;
import com.waterworld.watch.home.activity.SkillManagerActivity;
import com.waterworld.watch.home.activity.SkillContactListActivity;
import com.waterworld.watch.home.activity.SkillSportActivity;
import com.waterworld.watch.home.adapter.HomeFragmentAdapter;
import com.waterworld.watch.home.bean.SkillManagerTrueBean;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;

import java.util.ArrayList;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/28 13:55
 * 主要作用：Home界面(碎片)
 */
public class HomeFragment extends BaseFragment implements View.OnClickListener {

    /**
     * 组件
     */
    private ImageView iv_new;//新闻轮播图
    private ImageView iv_smile;//笑脸
    private EditText et_search;//编辑框
    private ImageButton ib_enter;//开始搜索
    /**
     * 其他
     */
    private RecyclerView skillList;//功能列表
    private HomePagerActivity parentActivity;//宿主Activity
    private List<SkillManagerTrueBean> skillListData;//数据源
    private HomeFragmentAdapter skillAdapter;//功能适配器
    private IHomeManager iHomeManager = HomeManager.getInstance();

    @Override
    protected int setContentView() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        parentActivity = (HomePagerActivity) getActivity();
        bindView();
        bindClick();
        initRecycler();
        skillClick();
    }

    @Override
    protected void lazyLoad() {

    }

    /**
     * 绑定View
     */
    private void bindView() {
        skillList = findViewById(R.id.rv_skill_list);
        iv_new = findViewById(R.id.iv_new);
        iv_smile = findViewById(R.id.iv_smile);
        et_search = findViewById(R.id.et_search);
        ib_enter = findViewById(R.id.ib_enter);
    }

    private void initRecycler() {
        iHomeManager.getAllFunction(new BaseObserverListener<BaseResultBean>() {
            @Override
            public void onCompleted() {
                Log.i("全部功能列表", "--成功！");
            }

            @Override
            public void onError(Throwable e) {
                Log.i("全部功能列表", "--失败：" + e);
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {

                Log.i("全部功能列表", (String) baseResultBean.getData());

            }
        });

        skillListData = new ArrayList<>();
        //模拟数据
        skillListData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        skillListData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        skillListData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        skillListData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        skillListData.add(new SkillManagerTrueBean("运动统计", getResources().getDrawable(R.drawable.ic_skill_sports)));
        skillListData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        skillListData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        skillListData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        skillListData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        skillListData.add(new SkillManagerTrueBean("运动统计", getResources().getDrawable(R.drawable.ic_skill_sports)));
        skillListData.add(new SkillManagerTrueBean("课程表", getResources().getDrawable(R.drawable.ic_skill_curriculum)));
        skillListData.add(new SkillManagerTrueBean("课程表", getResources().getDrawable(R.drawable.ic_skill_curriculum)));
        skillListData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        skillListData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        skillListData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        skillListData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        skillListData.add(new SkillManagerTrueBean("运动统计", getResources().getDrawable(R.drawable.ic_skill_sports)));
        skillListData.add(new SkillManagerTrueBean("课程表", getResources().getDrawable(R.drawable.ic_skill_curriculum)));
        //把列表的最后一项item设置为增加功能按钮
        skillListData.add(skillListData.size(), new SkillManagerTrueBean("增加功能", getResources().getDrawable(R.drawable.ic_skill_add)));
        skillAdapter = new HomeFragmentAdapter(parentActivity, skillListData);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(parentActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        skillList.setLayoutManager(new GridLayoutManager(parentActivity, 3));
        skillList.setNestedScrollingEnabled(false);
        skillList.setAdapter(skillAdapter);
    }

    /**
     * 绑定可点击的View
     */
    private void bindClick() {
        iv_new.setOnClickListener(this);
        iv_smile.setOnClickListener(this);
        et_search.setOnClickListener(this);
        ib_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_new:
                break;
            case R.id.iv_smile:
                break;
            case R.id.et_search:
                break;
            case R.id.ib_enter:
                break;
        }
    }

    /**
     * 技能点击事件
     */
    private void skillClick() {
        skillAdapter.setAddSkillClick(new HomeFragmentAdapter.addSkillClick() {
            @Override
            public void addSkill(View view, int position) {
                if (skillAdapter.getItemCount() != 0 && skillAdapter.getItemCount() - 1 == position) {
                    Intent intent = new Intent(parentActivity, SkillManagerActivity.class);
                    startActivity(intent);
                } else {
                    if (skillAdapter.getMData().get(position).getSkillName().equals("打电话")) {
                        Log.i("技能", "打电话");
                        callPhone("17763693759");
                    } else if (skillAdapter.getMData().get(position).getSkillName().equals("通讯录")) {
                        Intent intent = new Intent(parentActivity, SkillContactListActivity.class);
                        startActivity(intent);
                    } else if (skillAdapter.getMData().get(position).getSkillName().equals("运动统计")) {
                        Intent intent = new Intent(parentActivity, SkillSportActivity.class);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    /**
     * 打电话
     */
    private void callPhone(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + number);
        intent.setData(data);
        startActivity(intent);
    }
}
