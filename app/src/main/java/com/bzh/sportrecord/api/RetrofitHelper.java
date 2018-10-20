package com.bzh.sportrecord.api;

import android.content.Context;

import com.bzh.sportrecord.App;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
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

    //添加动态head
    private static final OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor()).build();

    private static final GsonConverterFactory gsonFactory = GsonConverterFactory.create(new GsonBuilder().create());

    public static RetrofitHelper getInstance() {
        if (instance == null) {
            instance = new RetrofitHelper();
        }
        return instance;
    }

    private void init() {
        resetApp();
    }

    private void resetApp() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://" + App.ip + ":8090/") //192.168.1.196:8090  215   192.168.31.75
                .client(client)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public RetrofitService getServer() {
        return mRetrofit.create(RetrofitService.class);
    }

    //动态head
    public static class RequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request;
            if (App.getMainAttrs().getLoginSign().getValue() != null && App.getMainAttrs().getLoginSign().getValue()) {
                request = original.newBuilder()
                        .header("X_Auth_Token", App.getToken())
                        //.header("Accept", "application/vnd.yourapi.v1.full+json")
                        .method(original.method(), original.body())
                        .build();
            } else {
                request = original.newBuilder()
                        .method(original.method(), original.body())
                        .build();
            }

            return chain.proceed(request);
        }
    }
}
