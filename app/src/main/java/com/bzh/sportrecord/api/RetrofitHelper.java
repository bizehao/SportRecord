package com.bzh.sportrecord.api;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.MainAttrs;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
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

    private MainAttrs mainAttrs;

    private Retrofit mRetrofit;

    private RetrofitService retrofitService;

    public RetrofitHelper(MainAttrs mainAttrs) {
        this.mainAttrs = mainAttrs;
        mRetrofit = new Retrofit.Builder()
                .baseUrl("http://" + App.ip + ":8090/") //192.168.1.196:8090  215   192.168.31.75
                .client(client)
                .addConverterFactory(gsonFactory)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    //添加动态head
    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new RequestInterceptor()).build();

    private GsonConverterFactory gsonFactory = GsonConverterFactory.create(new GsonBuilder().create());

    public RetrofitService getServer() {
        if(retrofitService == null){
            retrofitService = mRetrofit.create(RetrofitService.class);
        }
        return retrofitService;
    }

    //动态head
    public class RequestInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request original = chain.request();
            Request request;
            if (mainAttrs.getLoginSign().getValue() != null && mainAttrs.getLoginSign().getValue()) {
                request = original.newBuilder()
                        .header("X_Auth_Token", App.getToken())
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

    @SuppressWarnings("CheckResult")
    public <T> void successHandler(Observable<T> observable, callBack callBack) {
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> {
                    callBack.run(t);
                }, throwable -> {
                    throwable.printStackTrace();
                    throwable.getMessage();
                    System.out.println("网络请求发生错误");
                });
    }

    public interface callBack {
        <T> void run(T t);
    }
}
