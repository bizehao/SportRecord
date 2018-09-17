package com.bzh.sportrecord.api;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 网络处理类
 */
public class RetrofitHelper {

    private Retrofit mRetrofit = null;

    private static RetrofitHelper instance = null;

    private RetrofitHelper() {
        init();
    }

    private static final OkHttpClient client = new OkHttpClient();

    private static final GsonConverterFactory gsonFactory = GsonConverterFactory.create(new GsonBuilder().create());

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private void init(){
        resetApp();
    }

    private void resetApp(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.1.200:8090/")
                .client(client)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }
}
