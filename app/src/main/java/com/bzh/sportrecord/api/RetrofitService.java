package com.bzh.sportrecord.api;

import com.bzh.sportrecord.model.ApiLogin;
import com.bzh.sportrecord.model.ApiUserInfo;
import com.bzh.sportrecord.model.Book;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    //测试
    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name,
                                   @Query("tag") String tag,
                                   @Query("start") int start,
                                   @Query("count") int count);

    //登陆
    @FormUrlEncoded
    @POST("user/login")
    Observable<ApiLogin> login(@Field("username") String username,
                               @Field("password") String password);
    //获取用户信息
    @GET("user/get-user-info")
    Observable<ApiUserInfo> getUserInfo(@Query("id")String id);

}
