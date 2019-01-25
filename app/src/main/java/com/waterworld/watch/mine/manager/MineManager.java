package com.waterworld.watch.mine.manager;

import android.content.Context;

import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.net.NetManager;
import com.waterworld.watch.mine.interfaces.IMineManager;
import com.waterworld.watch.mine.interfaces.ModifyAccountResultListener;

import java.io.File;

import rx.Subscriber;

/**
 * Created by nhuan
 * Time:2018/12/19.
 */

public class MineManager implements IMineManager {

    private static MineManager mInstance;
    private Context context;

    public static MineManager getInstance(){
        if(mInstance == null){
            synchronized (MineManager.class){
                if(mInstance == null){
                    mInstance = new MineManager(MyApplication.getContext());
                }
            }
        }
        return mInstance;
    }

    private MineManager(Context context){
        super();
        this.context = context;
    }

    /**
     * 修改密码
     * @param newPwd
     * @param oldPwd
     * @param baseObserverListener
     */
    @Override
    public void modifyPwd(String newPwd, String oldPwd, final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().modifyPwd(newPwd, oldPwd, new Subscriber<BaseResultBean>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    @Override
    public void uploadImg(File file, final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().uploadImg(file, new Subscriber<BaseResultBean<String>>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean<String> baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    /**
     * 修改手机号
     * @param newPhone 新的手机号
     * @param authCode 验证码
     * @param pwd 密码
     * @param modifyAccountResultListener
     */
    @Override
    public void modifyPhone(String newPhone, String authCode, String pwd, final ModifyAccountResultListener modifyAccountResultListener) {
        NetManager.getInstance().modifyPhone(newPhone, authCode, pwd, new Subscriber<BaseResultBean>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                modifyAccountResultListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {
                if(baseResultBean.getCode() == 0){
                    modifyAccountResultListener.onSuccess(baseResultBean);
                }else{
                    modifyAccountResultListener.onFail(baseResultBean);
                }
            }
        });
    }

    @Override
    public void modifyUserInfo(String head, String name, String relationWithBaby, final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().modifyUserInfo(head, name, relationWithBaby, new Subscriber<BaseResultBean>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }
}
