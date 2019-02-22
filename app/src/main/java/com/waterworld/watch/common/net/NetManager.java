package com.waterworld.watch.common.net;

import com.waterworld.watch.common.bean.BindWatchBean;
import com.waterworld.watch.common.bean.BindWatchUserBean;
import com.waterworld.watch.common.bean.UserInfoBean;
import com.waterworld.watch.common.bean.WatchUserInfoBean;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Url;
import rx.Observer;
import rx.Subscriber;


/**
 * Created by nhuan
 * Time:2018/12/14.
 */

public class NetManager implements INetManager {

    private static NetManager mInstance;

    public static NetManager getInstance() {
        if (mInstance == null) {
            synchronized (NetManager.class) {
                if (mInstance == null) {
                    mInstance = new NetManager();
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取短信验证码
     *
     * @param phone      手机号码
     * @param type       验证类型(0-登录，1-注册，2-修改密码，3-修改手机号-需要认证)
     * @param subscriber
     */
    @Override
    public void getAuthCode(String phone, String type, Subscriber<BaseResultBean<String>> subscriber) {
        RetrofitClient.getInstance().getApiService().getAuthCode(UrlContants.GET_AUTH_CODE, phone, type)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 注册
     *
     * @param phone      手机号码
     * @param pwd        密码
     * @param authCode   验证码
     * @param subscriber
     */
    @Override
    public void register(String phone, String pwd, String authCode, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", pwd);
        map.put("authCode", authCode);
        RetrofitClient.getInstance().getApiService().register(UrlContants.REGISTER, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 登录
     *
     * @param phone      手机号
     * @param type       登录类型（0-账号密码登录，1-验证码登录）
     * @param pwd        密码
     * @param subscriber
     */
    @Override
    public void login(String phone, String type, String pwd, Subscriber<BaseResultBean<String>> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("type", type);
        if (type.equals("0")) {     //密码登录
            map.put("pwd", pwd);
        } else if (type.equals("1")) {     //验证码登录
            map.put("authCode", pwd);
        }
        map.put("loginDeviceType","1");
        RetrofitClient.getInstance().getApiService().login(UrlContants.LOGIN, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe((Observer) subscriber);
    }

    /**
     * 忘记密码
     *
     * @param phone      手机号
     * @param pwd        密码
     * @param authCode   验证码
     * @param subscriber
     */
    @Override
    public void forgetPwd(String phone, String pwd, String authCode, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("pwd", pwd);
        map.put("authCode", authCode);
        RetrofitClient.getInstance().getApiService().forgetPwd(UrlContants.FORGET_PASSWORD, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 修改密码
     *
     * @param newPwd     新密码
     * @param oldPwd     旧密码
     * @param subscriber
     */
    @Override
    public void modifyPwd(String newPwd, String oldPwd, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("newPwd", newPwd);
        map.put("oldPwd", oldPwd);
        RetrofitClient.getInstance().getApiService().modifyPwd(UrlContants.MODIFY_PASSWORD, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 修改账号
     *
     * @param newPhone   新手机号码
     * @param authCode   旧手机号码
     * @param pwd        原密码
     * @param subscriber
     */
    @Override
    public void modifyPhone(String newPhone, String authCode, String pwd, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("newPhone", newPhone);
        map.put("authCode", authCode);
        map.put("pwd", pwd);
        RetrofitClient.getInstance().getApiService().modifyPhone(UrlContants.MODIFY_PHONE, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 上传文件
     *
     * @param file       文件
     * @param subscriber
     */
    @Override
    public void uploadImg(File file, Subscriber<BaseResultBean<String>> subscriber) {
        //MediaType.parse("image/jpg") 告诉服务器上传文件类型,对应请求体中的Content-Type:image/jpg
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);
        RetrofitClient.getInstance().getApiService().uploadImg(UrlContants.UPLOAD_IMG, body)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 修改用户信息
     *
     * @param head             头像名，无修改可为null，系统图像名前缀加"sys_"用于标识
     * @param name             用户名
     * @param relationWithBaby 与宝贝关系
     * @param subscriber
     */
    @Override
    public void modifyUserInfo(String head, String name, String relationWithBaby, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("head", head);
        map.put("name", name);
        map.put("relationWithBaby", relationWithBaby);
        RetrofitClient.getInstance().getApiService().modifyUserInfo(UrlContants.MODIFY_USERINFO, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 获取用户信息
     *
     * @param subscriber
     */
    @Override
    public void getCurrentUser(Subscriber<BaseResultBean<UserInfoBean>> subscriber) {
        RetrofitClient.getInstance().getApiService().getCurrentUser(UrlContants.GET_CURRENT_USER)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 退出系统
     *
     * @param subscriber
     */
    @Override
    public void logout(Subscriber<BaseResultBean> subscriber) {
        RetrofitClient.getInstance().getApiService().logout(UrlContants.LOGOUT)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 绑定手表
     *
     * @param imei       IMEI号
     * @param subscriber
     */
    @Override
    public void bindWatch(String imei, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("imei", imei);
        RetrofitClient.getInstance().getApiService().bindWatch(UrlContants.BIND_WATCH, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 保存或更新手表用户
     *
     * @param imei       IMEI号
     * @param subscriber
     */
    @Override
    public void saveWatchUserInfo(String imei, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("imei", imei);
        RetrofitClient.getInstance().getApiService().saveWatchUserInfo(UrlContants.SAVE_WATCH_USERINFO, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 切换手表
     *
     * @param watchId    手表ID
     * @param subscriber
     */
    @Override
    public void switchWatch(Integer watchId, Subscriber<BaseResultBean> subscriber) {
        Map<String, Integer> map = new HashMap<>();
        map.put("watchId", watchId);
        RetrofitClient.getInstance().getApiService().switchWatch(UrlContants.SWITCH_WATCH, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 获取当前手表用户信息
     *
     * @param subscriber
     */
    @Override
    public void getCurrentWatchUserInfo(Subscriber<BaseResultBean<WatchUserInfoBean>> subscriber) {
        RetrofitClient.getInstance().getApiService().getCurrentWatchUserInfo(UrlContants.GET_CURRENT_WATCH_USERIFO)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 获取用户绑定手表列表
     *
     * @param subscriber
     */
    @Override
    public void listBindWatch(Subscriber<BaseResultBean<BindWatchBean[]>> subscriber) {
        RetrofitClient.getInstance().getApiService().listBindWatch(UrlContants.LIST_BIND_WATCH)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 获取绑定该手表的用户列表
     *
     * @param subscriber
     */
    @Override
    public void listBindWatchUser(Subscriber<BaseResultBean<BindWatchUserBean>> subscriber) {
        RetrofitClient.getInstance().getApiService().listBindWatchUser(UrlContants.LIST_BIND_WATCHUSER)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 移交管理权限
     *
     * @param authorizeeUserId 被授权用户标识
     * @param subscriber
     */
    @Override
    public void transferAuthority(Integer authorizeeUserId, Subscriber<BaseResultBean> subscriber) {
        Map<String, Integer> map = new HashMap<>();
        map.put("authorizeeUserId", authorizeeUserId);
        RetrofitClient.getInstance().getApiService().transferAuthority(UrlContants.TRANSFER_AUTHORITY, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 解除绑定（自身或其他用户）
     *
     * @param unbundlingUserId 被授权用户标识
     * @param subscriber
     */
    @Override
    public void unbindWatch(Integer unbundlingUserId, Subscriber<BaseResultBean> subscriber) {
        Map<String, Integer> map = new HashMap<>();
        map.put("unbundlingUserId", unbundlingUserId);
        RetrofitClient.getInstance().getApiService().unbindWatch(UrlContants.UNBIND_WATCH, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 恢复出厂设置
     *
     * @param subscriber
     */
    @Override
    public void resumeFactory(Subscriber<BaseResultBean> subscriber) {
        RetrofitClient.getInstance().getApiService().resumeFactory(UrlContants.RESUME_FACTORY)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 编辑主页功能区
     *
     * @param subscriber
     */
    @Override
    public void editHomePagerFunction(String functionSigns, Subscriber<BaseResultBean> subscriber) {
        Map<String, String> map = new HashMap<>();
        map.put("functionSigns", functionSigns);
        RetrofitClient.getInstance().getApiService().editHomePagerFunction(UrlContants.EDIT_HOME_PAGER_FUNCTION, map)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 获取所有功能
     *
     * @param subscriber
     */
    @Override
    public void getAllFunction(Subscriber<BaseResultBean> subscriber) {
        RetrofitClient.getInstance().getApiService().getAllFunction(UrlContants.GET_ALL_FUNCATION)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }

    /**
     * 获取未应用的功能
     *
     * @param subscriber
     */
    @Override
    public void getNotUseFunction(Subscriber<BaseResultBean> subscriber) {
        RetrofitClient.getInstance().getApiService().getNotUseFunction(UrlContants.GET_NOT_USE_FUNCATION)
                .compose(RetrofitClient.getInstance().schedulersTransformer())
                .subscribe(subscriber);
    }
}
