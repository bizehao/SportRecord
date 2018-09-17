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

    private Context mContext;

    private Retrofit mRetrofit = null;

    private static RetrofitHelper instance = null;

    private RetrofitHelper(Context mContext) {
        mContext = mContext;
        init();
    }

    private static final OkHttpClient client = new OkHttpClient();

    private static final GsonConverterFactory gsonFactory = GsonConverterFactory.create(new GsonBuilder().create());

    public static RetrofitHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitHelper(context);
        }
        return instance;
    }

    private void init(){
        resetApp();
    }

    private void resetApp(){
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.douban.com/v2/")
                .client(client)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public RetrofitService getServer(){
        return mRetrofit.create(RetrofitService.class);
    }
}