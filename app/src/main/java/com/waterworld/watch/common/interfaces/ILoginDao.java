package com.waterworld.watch.common.interfaces;

/**
 * Created by nhuan
 * Time:2018/12/13.
 * interface of login information data
 */

public interface ILoginDao<T> {

    void insert(T t);

    void upDate(T t);

    T getNewsLogin();

    void delPassword(int id);
}
