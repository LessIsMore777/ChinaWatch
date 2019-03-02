package com.waterworld.watch.home.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.waterworld.watch.R;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.home.activity.SkillManagerActivity;
import com.waterworld.watch.home.activity.SkillContactListActivity;
import com.waterworld.watch.home.activity.SkillSportActivity;
import com.waterworld.watch.home.adapter.HomeSkillEditAdapter;
import com.waterworld.watch.home.adapter.HomeSkillAdapter;
import com.waterworld.watch.home.bean.SkillManagerBean;
import com.waterworld.watch.home.bean.SkillManagerEditBean;
import com.waterworld.watch.home.constant.SkillConstant;
import com.waterworld.watch.home.event.FunctionsEvent;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;
import com.waterworld.watch.login.event.AutoLoginEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    @BindView(R.id.rv_skill_list)
    RecyclerView rv_skill_list;

    private HomePagerActivity parent;
    private HomeSkillAdapter skillAdapter;
    private List<SkillManagerBean> skillList = new ArrayList<>();
    private IHomeManager iHomeManager = HomeManager.getInstance();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        parent = (HomePagerActivity) getActivity();
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        return view;
    }

    private void initData() {
        iHomeManager.getCurrentWatchUserInfo(new BaseObserverListener<BaseResultBean<WatchUserInfoBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(parent.getApplicationContext(), getString(R.string.data_error));
            }

            @Override
            public void onNext(BaseResultBean<WatchUserInfoBean> baseResultBean) {

                WatchUserInfoBean.getInstance().setWatchInfo(baseResultBean.getData().getWatchInfo());
                WatchUserInfoBean.getInstance().setWatchUserInfo(baseResultBean.getData().getWatchUserInfo());

                String[] functions = baseResultBean.getData().getWatchUserInfo().getFunctionSign().split(",");
                operationFunction(functions,skillList);
                skillList.add(new SkillManagerBean(getString(R.string.add_function), getResources().getDrawable(R.drawable.ic_skill_add)));
                skillAdapter = new HomeSkillAdapter(parent, skillList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(parent);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                rv_skill_list.setLayoutManager(new GridLayoutManager(parent, 3));
                rv_skill_list.setAdapter(skillAdapter);
                skillClick();
            }
        });
    }

    private void operationFunction(String[] arr1, List<SkillManagerBean> arr2) {
        for (String function : arr1) {
            if (function.equals(SkillConstant.ADDRESS_BOOK)) {
                arr2.add(new SkillManagerBean(getString(R.string.address_book), getResources().getDrawable(R.drawable.ic_skill_contact)));
            } else if (function.equals(SkillConstant.BAN_CLASSES)){
                arr2.add(new SkillManagerBean(getString(R.string.ban_class),getResources().getDrawable(R.drawable.ic_skill_ban_class)));
            }else if (function.equals(SkillConstant.CURRICULUM)){
                arr2.add(new SkillManagerBean(getString(R.string.curriculum),getResources().getDrawable(R.drawable.ic_skill_curriculum)));
            }else if (function.equals(SkillConstant.INTIMATE_SET)){
                arr2.add(new SkillManagerBean(getString(R.string.intimate_set),getResources().getDrawable(R.drawable.ic_skill_intimate)));
            }else if (function.equals(SkillConstant.MSG_NOTICE)){
                arr2.add(new SkillManagerBean(getString(R.string.msg_notice),getResources().getDrawable(R.drawable.ic_skill_message)));
            }else if (function.equals(SkillConstant.ONE_WAY_LISTEN)){
                arr2.add(new SkillManagerBean(getString(R.string.one_way_listen),getResources().getDrawable(R.drawable.ic_skill_listener)));
            }else if (function.equals(SkillConstant.PHONE)){
                arr2.add(new SkillManagerBean(getString(R.string.phone),getResources().getDrawable(R.drawable.ic_skill_phone)));
            }else if (function.equals(SkillConstant.REMOTE_SHUTDOWN)){
                arr2.add(new SkillManagerBean(getString(R.string.remote_shutdown),getResources().getDrawable(R.drawable.ic_skill_switch)));
            }else if (function.equals(SkillConstant.SCHEDULE_REMINDER)){
                arr2.add(new SkillManagerBean(getString(R.string.schedule_reminder),getResources().getDrawable(R.drawable.ic_skill_ban_class)));
            }else if (function.equals(SkillConstant.SPORTS_COUNT)){
                arr2.add(new SkillManagerBean(getString(R.string.sport_count),getResources().getDrawable(R.drawable.ic_skill_sports)));
            }else if (function.equals(SkillConstant.TIMED_SHUTDOWN)){
                arr2.add(new SkillManagerBean(getString(R.string.timed_shutdown),getResources().getDrawable(R.drawable.ic_skill_alarm)));
            }
        }
    }

    /**
     * 技能点击事件
     */
    private void skillClick() {
        skillAdapter.setAddSkillClick(new HomeSkillAdapter.SkillClick() {
            @Override
            public void skillClick(View view, int position) {
                if (skillAdapter.getItemCount() != 0 && skillAdapter.getItemCount() - 1 == position) {
                    Intent intent = new Intent(parent, SkillManagerActivity.class);
                    startActivity(intent);
                } else {
                    if (skillAdapter.getMData().get(position).getSkillName().equals("打电话")) {
                        Log.i("技能", "打电话");
                        callPhone("17763693759");
                    } else if (skillAdapter.getMData().get(position).getSkillName().equals("通讯录")) {
                        Intent intent = new Intent(parent, SkillContactListActivity.class);
                        startActivity(intent);
                    } else if (skillAdapter.getMData().get(position).getSkillName().equals("运动统计")) {
                        Intent intent = new Intent(parent, SkillSportActivity.class);
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

    @Subscribe(sticky = true ,threadMode = ThreadMode.MAIN)
    public void loginStatus(AutoLoginEvent autoLoginEvent){
        if(autoLoginEvent.getAuto() == 1) {
            initData();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateFunctions(FunctionsEvent functionsEvent){
        skillList.clear();
        operationFunction(functionsEvent.getFunctions(),skillList);
        skillList.add(new SkillManagerBean(getString(R.string.add_function), getResources().getDrawable(R.drawable.ic_skill_add)));
        skillAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
