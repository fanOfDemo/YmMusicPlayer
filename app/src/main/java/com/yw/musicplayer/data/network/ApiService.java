package com.yw.musicplayer.data.network;

/**
 * 项目名称：YmMusicPlayer
 * 类描述：
 * 创建人：wengyiming
 * 创建时间：2016/11/21 16:39
 * 修改人：wengyiming
 * 修改时间：2016/11/21 16:39
 * 修改备注：
 */


import android.annotation.SuppressLint;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static final String TAG = "MrService";


    private static final String API_DEV_URL = "http://tingapi.ting.baidu.com/";
    private static final String API_PRODUCT_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting";

    private static final String WEB_DEV_URL = "http://";
    private static final String WEB_PRODUCT_URL = "http://";

    private static final String KEY_ENVIRONMENT = "api_environment";


    private static ApiService mInstance;
    private static boolean isDevEnvironment = true;

    private Retrofit mRetrofit;


    public static ApiService getInstance() {
        if (mInstance == null) {
            synchronized (ApiService.class) {
                if (mInstance == null) {
                    mInstance = new ApiService();
                }
            }
        }
        return mInstance;
    }

    public static String getBaseUrl() {
        return isDevEnvironment ? API_DEV_URL : API_PRODUCT_URL;
    }

    public <T> T createApi(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    private ApiService() {
        this(true);
    }

    private ApiService(boolean useRxJava) {
        final Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getClient());
        if (useRxJava) {
            builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        }
        mRetrofit = builder.build();
    }

    private OkHttpClient getClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        return new OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor(new HeadInterceptor())
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .hostnameVerifier(new HostnameVerifier() {
                    @SuppressLint("BadHostnameVerifier")
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .build();
    }

}