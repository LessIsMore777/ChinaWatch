package com.waterworld.watch.common.application;

import android.app.Application;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;


/**
 * 编写者：Created by SunnyTang
 * 时间：2018/11/27 14:26
 * 主要作用：该类的作用，用于在应用程序启动的时候，就初始化一些必要的参数。
 * 比如本程序的SDK。在这里面，给SDK设置秘钥之类的必要的参数，
 * 那么之后不管在哪里使用，都不用设置秘钥了。
 * 还有就是dialog，在这里面设置好dialog的风格，以后使用的时候，
 * 就不用在单独设置风格了
 */
public class MyApplication extends Application {

    private static MyApplication instance = null;
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        mContext = getApplicationContext();
    }

    /**
     * 得到Application的实例
     */
    public static MyApplication getInstance(){
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }

    public static void setViewSize(View view, int width, int height) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = width;
        params.height = height;
        view.setLayoutParams(params);
    }
}
