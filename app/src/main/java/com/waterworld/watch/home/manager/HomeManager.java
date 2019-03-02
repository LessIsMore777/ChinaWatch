package com.waterworld.watch.home.manager;

import android.content.Context;
import android.util.Log;

import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.bean.BindWatchBean;
import com.waterworld.watch.common.bean.UserInfoBean;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.common.dao.LoginDao;
import com.waterworld.watch.common.net.BaseObserverListener;
import com.waterworld.watch.common.net.BaseResultBean;
import com.waterworld.watch.common.net.NetManager;
import com.waterworld.watch.home.bean.RoleBean;
import com.waterworld.watch.home.interfaces.IHomeManager;
import com.waterworld.watch.login.manager.LoginManager;

import rx.Subscriber;

/**
 * Created by nhuan
 * Time:2019/1/17.
 */

public class HomeManager implements IHomeManager {

    private static HomeManager mInstance;
    private Context mContext;

    public static HomeManager getInstance(){
        if(mInstance == null){
            synchronized (LoginManager.class){
                if(mInstance == null){
                    mInstance = new HomeManager(MyApplication.getContext());
                }
            }
        }
        return mInstance;
    }

    private HomeManager(Context context){
        super();
        this.mContext = context;
    }

    /**
     * 绑定手表
     * @param imei 设备IMEI号
     * @param baseObserverListener
     */
    @Override
    public void bindWatch(String imei, String relationWithBaby,BaseObserverListener baseObserverListener) {
        NetManager.getInstance().bindWatch(imei, relationWithBaby, new Subscriber<BaseResultBean<RoleBean>>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean<RoleBean> baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    /**
     * 编辑主页功能区
     * @param functionSigns 功能标识集。
     * @param baseObserverListener
     */
    @Override
    public void editHomePageFunction(String functionSigns, BaseObserverListener baseObserverListener) {
        NetManager.getInstance().editHomePagerFunction(functionSigns, new Subscriber<BaseResultBean>() {
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

    /**
     * 获取所有功能
     * @param baseObserverListener
     */
    @Override
    public void getAllFunction(final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().getAllFunction(new Subscriber<BaseResultBean>() {
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

    /**
     * 获取未开启的功能
     * @param baseObserverListener
     */
    @Override
    public void getNotUseFunction(final BaseObserverListener baseObserverListener) {
        NetManager.getInstance().getNotUseFunction(new Subscriber<BaseResultBean>() {
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

    /**
     * 获取用户绑定手表列表
     */
    @Override
    public void listBindWatch(BaseObserverListener baseObserverListener) {
        NetManager.getInstance().listBindWatch(new Subscriber<BaseResultBean<BindWatchBean[]>>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean<BindWatchBean[]> baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    @Override
    public void getCurrentWatchUserInfo(BaseObserverListener baseObserverListener) {
        NetManager.getInstance().getCurrentWatchUserInfo(new Subscriber<BaseResultBean<WatchUserInfoBean>>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean<WatchUserInfoBean> baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    /**
     * 获取当前用户信息
     * @param baseObserverListener
     */
    @Override
    public void getCurrentUser(BaseObserverListener baseObserverListener) {
        NetManager.getInstance().getCurrentUser(new Subscriber<BaseResultBean<UserInfoBean>>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
            }

            @Override
            public void onNext(BaseResultBean<UserInfoBean> baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }

    /**
     * 保存手表用户信息
     * @param head
     * @param name
     * @param sex
     * @param birthday
     * @param grade
     * @param classes
     * @param height
     * @param weight
     * @param phone
     * @param baseObserverListener
     */
    @Override
    public void saveWatchUserInfo(String head, String name, Integer sex, String birthday, String grade, String classes, String height, String weight, String phone, BaseObserverListener baseObserverListener) {
        NetManager.getInstance().saveWatchUserInfo(head, name, sex, birthday, grade, classes, height, weight, phone, new Subscriber<BaseResultBean>() {
            @Override
            public void onCompleted() {
                baseObserverListener.onCompleted();
            }

            @Override
            public void onError(Throwable e) {
                baseObserverListener.onError(e);
                Log.e("nh",e.toString());
            }

            @Override
            public void onNext(BaseResultBean baseResultBean) {
                baseObserverListener.onNext(baseResultBean);
            }
        });
    }
}
