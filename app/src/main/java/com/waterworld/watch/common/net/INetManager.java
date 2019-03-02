package com.waterworld.watch.common.net;

import android.graphics.Bitmap;

import com.waterworld.watch.common.bean.BindWatchBean;
import com.waterworld.watch.common.bean.BindWatchUserBean;
import com.waterworld.watch.common.bean.UserInfoBean;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.home.bean.RoleBean;
import com.waterworld.watch.login.bean.BindBean;

import org.greenrobot.eventbus.Subscribe;

import java.io.File;

import rx.Subscriber;


/**
 * Created by nhuan
 * Time:2018/12/14.
 */

public interface INetManager {
    /**
     * 获取验证码
     */
    void getAuthCode(String phone, String type, Subscriber<BaseResultBean<String>> subscriber);

    /**
     * 注册
     */
    void register(String phone, String pwd, String authCode, Subscriber<BaseResultBean> subscriber);

    /**
     * 登录
     */
    void login(String phone, String type, String pwd, Subscriber<BaseResultBean<String>> subscriber);

    /**
     * 忘记密码
     */
    void forgetPwd(String phone, String pwd, String authCode, Subscriber<BaseResultBean> subscriber);

    /**
     * 修改密码
     */
    void modifyPwd(String newPwd, String oldPwd, Subscriber<BaseResultBean> subscriber);

    /**
     * 修改手机号
     */
    void modifyPhone(String newPhone, String authCode, String pwd, Subscriber<BaseResultBean> subscriber);

    /**
     * 头像上传
     */
    void uploadImg(File file, Subscriber<BaseResultBean<String>> subscriber);

    /**
     * 修改用户信息
     */
    void modifyUserInfo(String head, String name, String relationWithBaby, Subscriber<BaseResultBean> subscriber);

    /**
     * 获取用户信息
     */
    void getCurrentUser(Subscriber<BaseResultBean<UserInfoBean>> subscriber);

    /**
     * 退出系统
     */
    void logout(Subscriber<BaseResultBean> subscriber);

    /**
     * 绑定手表
     */
    void bindWatch(String imei, String relationWithBaby, Subscriber<BaseResultBean<RoleBean>> subscriber);

    /**
     * 保存或更新手表用户
     */
    void saveWatchUserInfo(String head,String name,Integer sex,String birthday,String grade,String classes,String height,String weight, String phone,Subscriber<BaseResultBean> subscriber);

    /**
     * 切换手表
     */
    void switchWatch(Integer watchId, Subscriber<BaseResultBean> subscriber);

    /**
     * 获取当前手表用户信息
     */
    void getCurrentWatchUserInfo(Subscriber<BaseResultBean<WatchUserInfoBean>> subscriber);

    /**
     * 获取用户绑定手表列表
     */
    void listBindWatch(Subscriber<BaseResultBean<BindWatchBean[]>> subscriber);

    /**
     * 获取绑定该手表的用户列表
     */
    void listBindWatchUser(Subscriber<BaseResultBean<BindWatchUserBean>> subscriber);

    /**
     * 移交管理权限
     */
    void transferAuthority(Integer authorizeeUserId, Subscriber<BaseResultBean> subscriber);

    /**
     * 解除绑定（自身或其他用户）
     * 被解绑用户标识；不传默认解绑自身
     */
    void unbindWatch(Integer unbundlingUserId, Subscriber<BaseResultBean> subscriber);

    /**
     * 恢复出厂设置
     */
    void resumeFactory(Subscriber<BaseResultBean> subscriber);

    /**
     * 编辑主页功能区
     */
    void editHomePagerFunction(String functionSigns,Subscriber<BaseResultBean> subscriber);

    /**
     * 获取所有功能
     */
    void getAllFunction(Subscriber<BaseResultBean> subscriber);

    /**
     * 获取未应用的功能
     */
    void getNotUseFunction(Subscriber<BaseResultBean> subscriber);
}
