package com.waterworld.watch.position.fragment;

import com.waterworld.watch.R;
import com.waterworld.watch.common.activity.HomoPagerActivity;
import com.waterworld.watch.common.fragment.BaseFragment;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/28 13:59
 * 主要作用：
 */
public class PositionFragment extends BaseFragment {

    private HomoPagerActivity parentActivity;//宿主Activity

    public PositionFragment(){

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_position;
    }

    @Override
    protected void initView() {
        parentActivity = (HomoPagerActivity) getActivity();
    }

    @Override
    protected void lazyLoad() {

    }

}
