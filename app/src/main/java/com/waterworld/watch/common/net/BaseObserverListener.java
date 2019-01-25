package com.waterworld.watch.common.net;

/**
 * Created by nhuan
 * Time:2018/12/13.
 */

public interface BaseObserverListener<T> {

    void onCompleted();

    void onError(Throwable e);

    void onNext(T t);
}
