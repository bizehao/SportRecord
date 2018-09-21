package com.bzh.sportrecord.api;

import com.bzh.sportrecord.model.ApiLogin;
import com.bzh.sportrecord.model.ApiRegister;
import com.bzh.sportrecord.model.ApiUserInfo;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    //登陆
    @FormUrlEncoded
    @POST("user/login")
    Observable<ApiLogin> login(@Field("username") String username,
                               @Field("password") String password);
    //获取用户信息
    @GET("user/get-user-info")
    Observable<ApiUserInfo> getUserInfo(@Query("username")String username);

    //注册用户
    @FormUrlEncoded
    @POST("user/register")
    Observable<ApiRegister> register(@FieldMap Map<String, String> requestRegister);
}
