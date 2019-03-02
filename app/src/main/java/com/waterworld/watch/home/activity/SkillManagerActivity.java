package com.waterworld.watch.home.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.TimeUtils;
import com.waterworld.watch.common.util.ToastUtils;
import com.waterworld.watch.home.adapter.HomeSkillEditAdapter;
import com.waterworld.watch.home.bean.SkillManagerEditBean;
import com.waterworld.watch.home.constant.SkillConstant;
import com.waterworld.watch.home.event.FunctionsEvent;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.home.manager.HomeManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SkillManagerActivity extends BaseActivity {

    @BindView(R.id.header_parent)
    LinearLayout header_parent;

    @BindView(R.id.header_back)
    ImageView header_back;

    @BindView(R.id.header_title)
    TextView header_title;

    @BindView(R.id.header_confirm)
    TextView header_confirm;

    @BindView(R.id.rv_mySkill)
    RecyclerView rv_mySkill;

    @BindView(R.id.rv_addSkill)
    RecyclerView rv_addSkill;

    private IHomeManager iHomeManager = HomeManager.getInstance();
    private HomeSkillEditAdapter homeSkillEditAdapter_mine;
    private HomeSkillEditAdapter homeSkillEditAdapter_add;

    private List<SkillManagerEditBean> mySkillList;
    private List<SkillManagerEditBean> addSkillList;

    private LinearLayoutManager layoutManager_mine;
    private LinearLayoutManager layoutManager_add;

    public static String[] allFunctions = {SkillConstant.ADDRESS_BOOK,SkillConstant.BAN_CLASSES,SkillConstant.CURRICULUM,SkillConstant.INTIMATE_SET,
            SkillConstant.MSG_NOTICE,SkillConstant.ONE_WAY_LISTEN,SkillConstant.PHONE, SkillConstant.REMOTE_SHUTDOWN,SkillConstant.SCHEDULE_REMINDER,
            SkillConstant.SPORTS_COUNT,SkillConstant.TIMED_SHUTDOWN};

    private String[] functions;
    private String[] notUseFunctions;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_skill);
        ButterKnife.bind(this);
        initView();
        initSkillRecycler();

    }


    private void initView() {
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(this) / 10);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_confirm.setVisibility(View.VISIBLE);
        header_title.setText(getString(R.string.edit_function));
    }


    private void initSkillRecycler() {
        mySkillList = new ArrayList<>();
        addSkillList = new ArrayList<>();
        iHomeManager.getCurrentWatchUserInfo(new BaseObserverListener<BaseResultBean<WatchUserInfoBean>>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(SkillManagerActivity.this, getString(R.string.data_error));
            }

            @Override
            public void onNext(BaseResultBean<WatchUserInfoBean> baseResultBean) {
                functions = baseResultBean.getData().getWatchUserInfo().getFunctionSign().split(",");
                //我的应用
                operationFunction(functions,mySkillList,getResources().getDrawable(R.drawable.ic_rect_delete));
                homeSkillEditAdapter_mine = new HomeSkillEditAdapter(SkillManagerActivity.this, mySkillList);
                layoutManager_mine = new LinearLayoutManager(SkillManagerActivity.this);
                layoutManager_mine.setOrientation(LinearLayoutManager.VERTICAL);
                rv_mySkill.setLayoutManager(new GridLayoutManager(SkillManagerActivity.this, 3));
                rv_mySkill.setAdapter(homeSkillEditAdapter_mine);

                //增加应用

                if (baseResultBean.getData().getWatchUserInfo().getFunctionSign().equals("")){
                    notUseFunctions = new String[allFunctions.length];
                }else {
                    notUseFunctions = new String[allFunctions.length - functions.length];
                }

                int z = 0;
                for (int i=0 ; i<allFunctions.length ; i++) {
                    Boolean flag = false;
                    for (int j=0 ; j<functions.length ; j++){
                        if(allFunctions[i].equals(functions[j])) {
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        notUseFunctions[z] = allFunctions[i];
                        z++;
                    }
                }
                operationFunction(notUseFunctions,addSkillList,getResources().getDrawable(R.drawable.ic_rect_add));
                homeSkillEditAdapter_add = new HomeSkillEditAdapter(SkillManagerActivity.this, addSkillList);
                layoutManager_add = new LinearLayoutManager(SkillManagerActivity.this);
                layoutManager_add.setOrientation(LinearLayoutManager.VERTICAL);
                rv_addSkill.setLayoutManager(new GridLayoutManager(SkillManagerActivity.this, 3));
                rv_addSkill.setAdapter(homeSkillEditAdapter_add);


                homeSkillEditAdapter_mine.setOnSkillClick(new HomeSkillEditAdapter.operateSkillClick() {
                    @Override
                    public void operateSkill(View view, int position) {
                        String[] temp1 = new String[notUseFunctions.length+1];
                        System.arraycopy(notUseFunctions,0,temp1,0,notUseFunctions.length);
                        temp1[notUseFunctions.length] = functions[position];
                        notUseFunctions = temp1;

                        int j = 0;
                        String[] temp2 = new String[functions.length-1];
                        for (int i=0 ; i<functions.length ; i++) {
                            if (i != position) {
                                temp2[j] = functions[i];
                                j++;
                            }
                        }
                        functions = temp2;

                        mySkillList.remove(position);
                        String[] newFunction = {notUseFunctions[notUseFunctions.length-1]};
                        operationFunction(newFunction,addSkillList,getResources().getDrawable(R.drawable.ic_rect_add));
                        homeSkillEditAdapter_mine.notifyDataSetChanged();
                        homeSkillEditAdapter_add.notifyDataSetChanged();

                    }
                });

                homeSkillEditAdapter_add.setOnSkillClick(new HomeSkillEditAdapter.operateSkillClick() {
                    @Override
                    public void operateSkill(View view, int position) {
                        String[] temp1;
                        if(baseResultBean.getData().getWatchUserInfo().getFunctionSign().equals("") && functions[0].equals("")) {
                            temp1 = new String[functions.length];
                            System.arraycopy(functions, 0, temp1, 0, functions.length);
                            temp1[functions.length-1] = notUseFunctions[position];
                            functions = temp1;
                        }else {
                            temp1 = new String[functions.length + 1];
                            System.arraycopy(functions, 0, temp1, 0, functions.length);
                            temp1[functions.length] = notUseFunctions[position];
                            functions = temp1;
                        }
                        int j = 0;
                        String[] temp2 = new String[notUseFunctions.length-1];
                        for (int i=0 ; i<notUseFunctions.length ; i++) {
                            if (i != position) {
                                temp2[j] = notUseFunctions[i];
                                j++;
                            }
                        }
                        notUseFunctions = temp2;

                        addSkillList.remove(position);
                        String[] newFunction = {functions[functions.length-1]};
                        operationFunction(newFunction,mySkillList,getResources().getDrawable(R.drawable.ic_rect_delete));
                        homeSkillEditAdapter_mine.notifyDataSetChanged();
                        homeSkillEditAdapter_add.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    private void operationFunction(String[] arr1, List<SkillManagerEditBean> arr2, Drawable drawable){
        for (String notUseFunction : arr1){
            if (notUseFunction.equals(SkillConstant.ADDRESS_BOOK)) {
                arr2.add(new SkillManagerEditBean(getString(R.string.address_book), getResources().getDrawable(R.drawable.ic_skill_contact),drawable));
            } else if (notUseFunction.equals(SkillConstant.BAN_CLASSES)){
                arr2.add(new SkillManagerEditBean(getString(R.string.ban_class),getResources().getDrawable(R.drawable.ic_skill_ban_class),drawable));
            }else if (notUseFunction.equals(SkillConstant.CURRICULUM)){
                arr2.add(new SkillManagerEditBean(getString(R.string.curriculum),getResources().getDrawable(R.drawable.ic_skill_curriculum),drawable));
            }else if (notUseFunction.equals(SkillConstant.INTIMATE_SET)){
                arr2.add(new SkillManagerEditBean(getString(R.string.intimate_set),getResources().getDrawable(R.drawable.ic_skill_intimate),drawable));
            }else if (notUseFunction.equals(SkillConstant.MSG_NOTICE)){
                arr2.add(new SkillManagerEditBean(getString(R.string.msg_notice),getResources().getDrawable(R.drawable.ic_skill_message),drawable));
            }else if (notUseFunction.equals(SkillConstant.ONE_WAY_LISTEN)){
                arr2.add(new SkillManagerEditBean(getString(R.string.one_way_listen),getResources().getDrawable(R.drawable.ic_skill_listener),drawable));
            }else if (notUseFunction.equals(SkillConstant.PHONE)){
                arr2.add(new SkillManagerEditBean(getString(R.string.phone),getResources().getDrawable(R.drawable.ic_skill_phone),drawable));
            }else if (notUseFunction.equals(SkillConstant.REMOTE_SHUTDOWN)){
                arr2.add(new SkillManagerEditBean(getString(R.string.remote_shutdown),getResources().getDrawable(R.drawable.ic_skill_switch),drawable));
            }else if (notUseFunction.equals(SkillConstant.SCHEDULE_REMINDER)){
                arr2.add(new SkillManagerEditBean(getString(R.string.schedule_reminder),getResources().getDrawable(R.drawable.ic_skill_schedule_reminder),drawable));
            }else if (notUseFunction.equals(SkillConstant.SPORTS_COUNT)){
                arr2.add(new SkillManagerEditBean(getString(R.string.sport_count),getResources().getDrawable(R.drawable.ic_skill_sports),drawable));
            }else if (notUseFunction.equals(SkillConstant.TIMED_SHUTDOWN)){
                arr2.add(new SkillManagerEditBean(getString(R.string.timed_shutdown),getResources().getDrawable(R.drawable.ic_skill_alarm),drawable));
            }
        }
    }

    @OnClick(R.id.header_back)
    void onBack() {
        finish();
    }

    @OnClick(R.id.header_confirm)
    void onConfirm() {
        iHomeManager.editHomePageFunction(TimeUtils.arrayToString(functions), new BaseObserverListener<BaseResultBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.showShort(SkillManagerActivity.this, getString(R.string.data_error));
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {
                if (baseResultBean.getCode() == 0) {
                    FunctionsEvent functionsEvent = new FunctionsEvent();
                    functionsEvent.setFunctions(functions);
                    EventBus.getDefault().post(functionsEvent);
                    finish();
                }else {
                    ToastUtils.showShort(SkillManagerActivity.this,baseResultBean.getMsg());
                }
            }
        });
    }
}
