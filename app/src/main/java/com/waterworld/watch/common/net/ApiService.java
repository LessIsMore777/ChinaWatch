package com.waterworld.watch.common.net;

import com.waterworld.watch.login.bean.LoginResponse;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by nhuan
 * Time:2018/12/12.
 */

public interface ApiService {
    //短信验证码
    @GET("{url}")
    Observable<BaseResultBean> getAuthCode(@Path(value = "url",encoded = true)String url, @Query("phone") String number);

    //注册
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> Register(@Path(value = "url",encoded = true)String url,@FieldMap Map<String,String> map);

    //登录
    @FormUrlEncoded
    @POST("{url}")
    Observable<LoginResponse> login(@Path(value = "url",encoded = true)String url, @FieldMap Map<String,String> map);

    //修改密码
    @FormUrlEncoded
    @POST("{url}")
    Observable<BaseResultBean> updatepwd(@Header("Cookies") String cookies,@Path(value = "url",encoded = true)String url,@FieldMap Map<String,String> map);
}
