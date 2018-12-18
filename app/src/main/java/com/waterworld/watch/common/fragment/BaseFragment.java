package com.waterworld.watch.common.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/28 14:01
 * 主要作用：
 */
public abstract class BaseFragment extends Fragment {

    protected boolean isInit = false;//是否初始化
    protected boolean isLoad = false;//是否加载
    private View view;
    Activity mActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        isInit = true;
        initView();
        isCanLoadData();
        return view;
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     * 该方法优于onCreate方法，也可以当做是生命周期的方法
     * 会通过isVisibleToUser告诉我们当前Fragment我们是否可见
     * 我们可以在可见的时候再进行网络加载。
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }
        if (getUserVisibleHint() && !isLoad) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 加载view
     */
    protected abstract void initView();

    /**
     * 设置Fragment要显示的布局
     */
    protected abstract int setContentView();

    /**
     * 获取设置的布局
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 找出对应的控件
     */
    protected <T extends View> T findViewById(int id) {
        return (T) getContentView().findViewById(id);
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {

    }

    /**
     * 内存重启后
     * 防止getActivity()空指针
     */
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity)context;
    }
}
