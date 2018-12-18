package com.waterworld.watch.me.fragment;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.HomoPagerActivity;
import com.waterworld.watch.common.fragment.BaseFragment;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/28 13:59
 * 主要作用：
 */
public class MeFragment extends BaseFragment {

    private HomoPagerActivity parentActivity;//宿主Activity

    public MeFragment(){

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initView() {
        parentActivity = (HomoPagerActivity) getActivity();
    }

    @Override
    protected void lazyLoad() {

    }
}
