package com.waterworld.watch.login.interfaces;

/**
 * Created by nhuan
 * Time:2018/12/14.
 */

public interface LoginResultListener<T> {

    void onSuccess(T t);

    void onFail(T t);

    void onError(T t);

}
