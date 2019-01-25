package com.waterworld.watch.home.interfaces;

import com.waterworld.watch.common.net.BaseObserverListener;

/**
 * Created by nhuan
 * Time:2019/1/17.
 */

public interface IHomeManager {
    /**
     * 绑定
     * @param imei 设备IMEI号
     * @param baseObserverListener
     */
    void bindWatch(String imei, BaseObserverListener baseObserverListener);

    /**
     * 编辑主页功能区
     * @param functionSigns 功能标识集。
     * @param baseObserverListener
     */
    void editHomePageFunction(String functionSigns,BaseObserverListener baseObserverListener);

    /**
     * 获取所有功能
     * @param baseObserverListener
     */
    void getAllFunction(BaseObserverListener baseObserverListener);

    /**
     * 获取未启用的功能
     * @param baseObserverListener
     */
    void getNotUseFunction(BaseObserverListener baseObserverListener);
}
