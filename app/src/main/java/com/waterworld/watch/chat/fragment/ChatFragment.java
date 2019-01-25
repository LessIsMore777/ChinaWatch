package com.waterworld.watch.chat.fragment;

import com.waterworld.watch.R;
import com.waterworld.watch.home.activity.HomePagerActivity;
import com.waterworld.watch.common.fragment.BaseFragment;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/28 13:59
 * 主要作用：
 */
public class ChatFragment extends BaseFragment {

    private HomePagerActivity parentActivity;//宿主Activity

    public ChatFragment(){

    }

    @Override
    protected int setContentView() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initView() {
        parentActivity = (HomePagerActivity) getActivity();
    }

    @Override
    protected void lazyLoad() {

    }

}
