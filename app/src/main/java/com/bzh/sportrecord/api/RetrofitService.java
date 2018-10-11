package com.bzh.sportrecord.api;

import com.bzh.sportrecord.model.ApiFriends;
import com.bzh.sportrecord.model.ApiLogin;
import com.bzh.sportrecord.model.ApiRegister;
import com.bzh.sportrecord.model.ApiUserInfo;
import com.bzh.sportrecord.model.ApiUserInfos;
import com.bzh.sportrecord.model.ApiaddFriends;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface RetrofitService {

    //登陆
    @FormUrlEncoded
    @POST("user/login")
    Observable<ApiLogin> login(@Field("username") String username,
                               @Field("password") String password);

    //获取用户信息,搜索用户
    @GET("user/get-user-info")
    Observable<ApiUserInfo> getUserInfo(@Query("username") String username);

    //注册用户
    @FormUrlEncoded
    @POST("user/register")
    Observable<ApiRegister> register(@FieldMap Map<String, String> requestRegister);

    //获取用户信息,搜索用户(模糊搜索,多个用户)
    @GET("user/get-user-infos")
    Observable<ApiUserInfos> getUserInfos(@Query("username") String username);

    //获取推荐用户
    @GET("user/recommendFriend")
    Observable<ApiUserInfos> getRecUser(@Query("username") String username);

    //添加好友
    @FormUrlEncoded
    @POST("user/addFriend")
    Observable<ApiaddFriends> addFriends(@Field("username") String username,
                                         @Field("friendName") String friendName,
                                         @Field("remarkName") String remarkName);

    //查询好友信息
    @GET("user/getFriends")
    Observable<ApiFriends> getFriends(@Query("username") String username);


    //上传图片
    @Multipart
    @POST("user/uploadPng")
    Observable<ResponseBody> uploadPng(@Part("username") RequestBody username,@Part MultipartBody.Part headPortrait);
}
