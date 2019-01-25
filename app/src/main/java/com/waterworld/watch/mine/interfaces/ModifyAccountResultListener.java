package com.waterworld.watch.mine.interfaces;

/**
 * Created by nhuan
 * Time:2019/1/15.
 */

public interface ModifyAccountResultListener<T> {

    void onSuccess(T t);

    void onFail(T t);

    void onError(T t);
}
