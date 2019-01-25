package com.waterworld.watch.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/27 18:16
 * 主要作用：
 */
public class HomePagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> mList;

    public HomePagerAdapter(FragmentManager fm,List<Fragment> list){
        super(fm);
        this.mList = list;
    }

    @Override
    public Fragment getItem(int position) {
        return mList.get(position);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

}
