package com.waterworld.watch.home.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.BaseActivity;
import com.waterworld.watch.common.util.ScreenAdapterUtil;
import com.waterworld.watch.common.util.SpacesItemDecoration;
import com.waterworld.watch.home.adapter.SkillManagerTrueAdapter;
import com.waterworld.watch.home.adapter.SkillManagerFalseAdapter;
import com.waterworld.watch.home.bean.SkillManagerFalseBean;
import com.waterworld.watch.home.bean.SkillManagerTrueBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/29 14:11
 * 主要作用：管理技能(活动)
 */
public class SkillManagerActivity extends BaseActivity implements View.OnClickListener {

    private final String TAG = "SkillManagerActivity";
    private SkillManagerActivity thisActivity;
    /**
     * 头部组件
     */
    private LinearLayout header_parent;
    private ImageView header_back;
    private TextView header_title;
    private Button header_finish;
    /**
     * 已启用的技能
     */
    private RecyclerView mySkillRecycler;
    private SkillManagerTrueAdapter mySkillAdapter;
    private ItemTouchHelper mySkillItemTouchHelper;
    private List<SkillManagerTrueBean> mySkillData;
    /**
     * 未启用的技能
     */
    private RecyclerView addSkillRecycler;
    private SkillManagerFalseAdapter addSkillAdapter;
    private ItemTouchHelper addSkillItemTouchHelper;
    private List<SkillManagerFalseBean> addSkillData;

    private LinearLayoutManager layoutManager;


    private SpacesItemDecoration spacesItemDecoration;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_add_skill);
        thisActivity = this;
        bindView();
        initView();
        bindClick();
        initMySkillRecycler();
        initAddSkillRecycler();
    }

    private void bindView() {
        header_parent = findViewById(R.id.header_parent);
        header_back = findViewById(R.id.header_back);
        header_title = findViewById(R.id.header_title);
        header_finish = findViewById(R.id.header_finish);
        mySkillRecycler = findViewById(R.id.rv_mySkill);
        addSkillRecycler = findViewById(R.id.rv_addSkill);
        layoutManager = new LinearLayoutManager(this);
    }

    private void initView() {
        //设置公共头部栏的高度为手机屏幕的11分之1
        setViewSize(header_parent, ViewGroup.LayoutParams.MATCH_PARENT, ScreenAdapterUtil.getHeightPx(thisActivity) / 12);
        header_back.setVisibility(View.VISIBLE);
        header_title.setVisibility(View.VISIBLE);
        header_finish.setVisibility(View.VISIBLE);
        header_title.setText("宝贝的手表");
    }

    /**
     * 初始化"增加应用"列表
     */
    private void initAddSkillRecycler(){
        addSkillData = new ArrayList<>();
        addSkillData.add(new SkillManagerFalseBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        addSkillData.add(new SkillManagerFalseBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        addSkillData.add(new SkillManagerFalseBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        addSkillData.add(new SkillManagerFalseBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));

        addSkillAdapter = new SkillManagerFalseAdapter(this,addSkillData);

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        addSkillRecycler.setLayoutManager(new GridLayoutManager(this,3));
        addSkillRecycler.setAdapter(addSkillAdapter);
    }

    /**
     * 初始化"我的应用"列表
     */
    private void initMySkillRecycler() {
        mySkillData = new ArrayList<>();
        mySkillData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        mySkillData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        mySkillData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        mySkillData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        mySkillData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        mySkillData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        mySkillData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        mySkillData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        mySkillData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        mySkillData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        mySkillData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        mySkillData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        mySkillData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        mySkillData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        mySkillData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        mySkillData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        mySkillData.add(new SkillManagerTrueBean("打电话", getResources().getDrawable(R.drawable.ic_skill_phone)));
        mySkillData.add(new SkillManagerTrueBean("通讯录", getResources().getDrawable(R.drawable.ic_skill_contact)));
        mySkillData.add(new SkillManagerTrueBean("单项聆听", getResources().getDrawable(R.drawable.ic_skill_listener)));
        mySkillData.add(new SkillManagerTrueBean("远程拍摄", getResources().getDrawable(R.drawable.ic_skill_switch)));
        mySkillAdapter = new SkillManagerTrueAdapter(this, mySkillData, 18, 18, 3, ScreenAdapterUtil.getWidthPx(this));
        //Recycler设置布局类型
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mySkillRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        mySkillRecycler.setAdapter(mySkillAdapter);
        //设置item的拖拽和滑动监听
        mySkillItemTouchHelper = new ItemTouchHelper(mCallBack);
        mySkillItemTouchHelper.attachToRecyclerView(mySkillRecycler);
        //spacesItemDecoration = new SpacesItemDecoration(18, 18);
        //mySkillRecycler.addItemDecoration(spacesItemDecoration);
        Log.i("边距：","初始化："+mySkillRecycler.getItemDecorationCount());
        //mySkillRecycler.addItemDecoration(new CommonItemDecoration(0,8,5,5,5,5));
    }

    private void bindClick(){
        header_back.setOnClickListener(this);
        header_finish.setOnClickListener(this);
    }

    ItemTouchHelper.Callback mCallBack = new ItemTouchHelper.Callback() {

        /**
         * 设置标志，表示你的触摸是拖拽还是滑动
         * @param recyclerView recycler
         * @param viewHolder 选中的viewHolder
         * @return 设置完成的标志位
         */
        @Override
        public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
            if (mySkillRecycler.getLayoutManager() instanceof GridLayoutManager) {
                //网格类型的布局，拖拽方向是上下左右
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //左右滑动的标志位，不想用的话直接设置为0，表示关闭。
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            } else {
                final int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
                final int swipeFlags = 0;
                return makeMovementFlags(dragFlags, swipeFlags);
            }
        }

        /**
         * 拖拽的时候
         * @param recyclerView recycler
         * @param fromHolder 初始holder
         * @param targetHolder 目标holder
         * @return 消费或者为消费
         */
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder fromHolder, @NonNull RecyclerView.ViewHolder targetHolder) {
            int fromPosition = fromHolder.getAdapterPosition();//得到拖动ViewHolder的position
            int toPosition = targetHolder.getAdapterPosition();//得到目标ViewHolder的position
            if (fromPosition < toPosition) {
                for (int i = fromPosition; i < toPosition; i++) {
                    Collections.swap(mySkillData, i, i + 1);
                }
            } else {
                for (int i = fromPosition; i > toPosition; i--) {
                    Collections.swap(mySkillData, i, i - 1);
                }
            }
//            if (mySkillRecycler.getItemDecorationCount() == 2) {
//                mySkillRecycler.removeItemDecoration(spacesItemDecoration);
//
//            } else {
//                mySkillRecycler.addItemDecoration(spacesItemDecoration);
//            }

            mySkillAdapter.notifyItemMoved(fromPosition, toPosition);
            Log.i("边距：","拖拽："+mySkillRecycler.getItemDecorationCount());
            return true;
        }

        /**
         * 左右滑动的时候监听
         */
        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        }

        /**
         * 设置是否开启左右滑动
         */
        @Override
        public boolean isItemViewSwipeEnabled() {
            return super.isItemViewSwipeEnabled();
        }

        /**
         * 设置是否开启拖拽
         */
        @Override
        public boolean isLongPressDragEnabled() {
            return super.isLongPressDragEnabled();
        }

        /**
         * 当长按选中item的时候（拖拽开始的时候）调用
         * @param viewHolder 所选中的item
         * @param actionState 表示拖拽状态，有3个值。
         * ACTION_STATE_IDLE：闲置状态
         * ACTION_STATE_SWIPE：滑动状态
         * ACTION_STATE_DRAG：拖拽状态
         */
        @Override
        public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {

        }

        /**
         * 当手指松开的时候（拖拽完成的时候）调用
         * @param recyclerView recycler
         * @param viewHolder 所选中的item
         */
        @Override
        public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {

        }
    };


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header_back:
                finish();
                break;
            case R.id.header_finish:
                //保存设置
                break;
        }
    }
}
