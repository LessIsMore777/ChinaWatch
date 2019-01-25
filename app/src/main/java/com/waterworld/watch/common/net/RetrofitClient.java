package com.waterworld.watch.common.net;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.waterworld.watch.common.application.MyApplication;
import com.waterworld.watch.common.util.AppManager;
import com.waterworld.watch.login.activity.VerifyLoginActivity;
import com.waterworld.watch.login.bean.LoginBean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by nhuan
 * Time:2018/12/12.
 * Retrofit frame , be used in network request.
 */

public class RetrofitClient {
    public static final String TAG = RetrofitClient.class.getSimpleName();
    private static final Long DEFAULT_TIMEOUT = 5L;
    private ApiService apiService;


    private static RetrofitClient mInstance;

    public static RetrofitClient getInstance() {
        if(mInstance == null){
            synchronized (RetrofitClient.class){
                if(mInstance == null){
                    mInstance = new RetrofitClient(MyApplication.getInstance());
                }
            }
        }
        return mInstance;
    }

    public static RetrofitClient getInstance(String url) {
        if (mInstance == null) {
            synchronized (RetrofitClient.class) {
                if (mInstance == null) {
                    mInstance = new RetrofitClient(MyApplication.getInstance(),url);
                }
            }
        }
        return mInstance;
    }

    private RetrofitClient(Context context) {
        this(context, null);
    }

    private RetrofitClient(Context context, String url) {
        if (TextUtils.isEmpty(url)) {
            url = UrlContants.BASE_URL;
        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                //add cache
                .cache(new Cache(new File(context.getExternalFilesDir("okhttpCache"), ""), 14 * 1024 * 100))
                .addInterceptor(new CacheInterceptor())
                .addNetworkInterceptor(new CacheInterceptor())
                //add header
                .addInterceptor(new HeaderInterceptor())
                //add log
                .addInterceptor(new LoggingInterceptor())
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                //Gson序列化直接把json数据转化为实体类
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(url)
                .build();

        apiService = retrofit.create(ApiService.class);
    }

    public Observable.Transformer schedulersTransformer() {
        return new Observable.Transformer() {
            @Override
            public Object call(Object o) {
                return ((Observable)o).subscribeOn(Schedulers.io()) //IO线程加载数据
                        .unsubscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()); //主线程显示数据
            }
        };
    }

    public ApiService getApiService() {
        return apiService;
    }

    /**
     * 添加请求头需要携带的参数
     */
    public class HeaderInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            Request requestBuilder = request.newBuilder()
                    .addHeader("Cookie","JSESSIONID="+MyApplication.getInstance().getSessionID())
                    .method(request.method(), request.body())
                    .build();

            Response response = chain.proceed(requestBuilder);
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            String bodyString = responseBody.string();
            com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject)com.alibaba.fastjson.JSONObject.parse(bodyString);
            if(object.getIntValue("code") == 2 && LoginBean.getInstance().getPassword().equals("")){
                MyApplication.getContext().startActivity(new Intent(MyApplication.getContext(), VerifyLoginActivity.class));
                AppManager.finishAllActivity();
                return null;
            }
            if(object.getIntValue("code") == 2){
                getNewCookie();
                Request newRequest = request.newBuilder()
                        .addHeader("Cookie","JSESSIONID="+MyApplication.getInstance().getSessionID())
                        .method(request.method(), request.body())
                        .build();
                return chain.proceed(newRequest);
            }
            return response;
        }

        /**
         * 同步请求，获取最新cookie
         */
        private void getNewCookie() throws IOException{
            RequestBody requestBody = new FormBody.Builder()
                    .add("phone",LoginBean.getInstance().getUsername())
                    .add("type","0")
                    .add("pwd",LoginBean.getInstance().getPassword())
                    .build();
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(UrlContants.BASE_URL+UrlContants.LOGIN).post(requestBody).build();
            Response response = client.newCall(request).execute();
            String result = response.body().string();
            com.alibaba.fastjson.JSONObject object = (com.alibaba.fastjson.JSONObject) com.alibaba.fastjson.JSONObject.parse(result);
            String SessionId = object.getString("data");
            MyApplication.getInstance().setSessionID(SessionId);
        }
    }

    /**
     * 设置缓存的拦截器
     */
    public class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (NetworkUtils.isNetworkAvailable(MyApplication.getContext())) {
                String cacheControl = request.cacheControl().toString();
                Log.e(TAG, "network is available");
                return response.newBuilder().header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                Log.e(TAG, "network is not available");
                return response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + "60 * 60 * 24 * 7")
                        .removeHeader("Pragma").build();
            }
        }
    }

    /**
     * log打印
     */
    public class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，需要什么都可以从这里拿
            Request request = chain.request();
            //请求发起的时间
            long t1 = System.nanoTime();
            String method = request.method();
            JSONObject jsonObject = new JSONObject();
            if ("POST".equals(method) || "PUT".equals(method)) {
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    if (body != null) {
                        for (int i = 0; i < body.size(); i++) {
                            try {
                                jsonObject.put(body.name(i), body.encodedValue(i));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Log.e("request", String.format("send request %s on %s  %n RequestParams: %s%n Method:%s",
                            request.url(), chain.connection(), jsonObject.toString(), request.method()));
                } else {
                    Buffer buffer = new Buffer();
                    RequestBody requestBody = request.body();
                    if (requestBody != null) {
                        request.body().writeTo(buffer);
                        String body = buffer.readUtf8();
                        Log.e("request", String.format("send request %s on %s  %n RequestParams: %s%n Method:%s",
                                request.url(), chain.connection(), body, request.method()));
                    }
                }
            } else {
                Log.e("request", String.format("send request %s on %s%n Method: %s",
                        request.url(), chain.connection(), request.method()));
            }
            Response response = chain.proceed(request);
            //收到响应的时间
            long t2 = System.nanoTime();
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.e("request",
                    String.format("Retrofit respond: %s %n return json:%s %n time：%.1fms",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6d
                    ));
            return response;
        }
    }
}


