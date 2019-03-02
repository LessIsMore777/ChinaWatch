package com.waterworld.watch.common.net;


import com.waterworld.watch.common.bean.BindWatchBean;
import com.waterworld.watch.common.bean.BindWatchUserBean;
import com.waterworld.watch.common.bean.UserInfoBean;
import com.waterworld.watch.common.bean.WatchUserInfoBean;
import com.waterworld.watch.home.bean.RoleBean;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nhuan
 * Time:2018/12/12.
 */

public interface ApiService {
    //获取验证码
    @GET("{url}")
    Observable<BaseResultBean<String>> getAuthCode(@Path(value = "url",encoded = true)String url, @Query("phone") String phone, @Query("type") String type);

    //注册
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> register(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,String> map);

    //登录
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean<String>> login(@Path(value = "url",encoded = true)String url, @FieldMap Map<String,String> map);

    //忘记密码
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> forgetPwd(@Path(value = "url",encoded = true) String url,@FieldMap Map<String,String> map);

    //修改密码
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> modifyPwd(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,String> map);

    //修改手机号码
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> modifyPhone(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,String> map);

    //上传文件
    @Multipart  //采用multipart/from-data的请求方式,对应请求头中的Content-Type:multipart/from-data
    @POST("{url}")
    Observable<BaseResultBean<String>> uploadImg(@Path(value = "url",encoded = true)String url, @Part MultipartBody.Part body);

    //修改用户信息
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> modifyUserInfo(@Path(value = "url",encoded = true)String url ,@FieldMap Map<String,String> map);

    //获取用户信息
    @GET("{url}")
    Observable<BaseResultBean<UserInfoBean>> getCurrentUser(@Path(value = "url",encoded = true)String url);

    //退出系统
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> logout(@Path(value = "url",encoded = true)String url);

    //绑定手表
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean<RoleBean>> bindWatch(@Path(value = "url",encoded = true)String url, @FieldMap Map<String,String> map);

    //保存或更新手表用户
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> saveWatchUserInfo(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,Object> map);

    //切换手表
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> switchWatch(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,Integer> map);

    //获取当前手表用户信息
    @GET("{url}")
    Observable<BaseResultBean<WatchUserInfoBean>> getCurrentWatchUserInfo(@Path(value = "url",encoded = true)String url);

    //获取用户绑定手表列表
    @GET("{url}")
    Observable<BaseResultBean<BindWatchBean[]>> listBindWatch(@Path(value = "url",encoded = true)String url);

    //获取绑定该手表的用户列表
    @GET("{url}")
    Observable<BaseResultBean<BindWatchUserBean[]>> listBindWatchUser(@Path(value = "url",encoded = true)String url);

    //移交管理权限
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> transferAuthority(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,Integer> map);

    //解除绑定（自身或其他用户）
    //被解绑用户标识；不传默认解绑自身
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> unbindWatch(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,Integer> map);

    //恢复出厂设置
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> resumeFactory(@Path(value = "url",encoded = true)String url);

    //编辑主页功能区
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> editHomePagerFunction(@Path(value = "url", encoded = true) String url, @FieldMap Map<String, String> map);

    //获取所有功能
    @GET("{url}")
    Observable<BaseResultBean<String>> getAllFunction(@Path(value = "url", encoded = true) String url);

    //获取未应用的功能
    @POST("{url}")
    Observable<BaseResultBean<String>> getNotUseFunction(@Path(value = "url", encoded = true) String url);
}
