package com.bzh.sportrecord.api;

import com.bzh.sportrecord.model.Book;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitService {

    @GET("book/search")
    Observable<Book> getSearchBook(@Query("q") String name,
                                   @Query("tag") String tag,
                                   @Query("start") int start,
                                   @Query("count") int count);

    @FormUrlEncoded
    @POST("user/login")
    Observable<JSONObject> login(@Field("username") String username,
                                 @Field("password") String password);


}
