package com.bzh.sportrecord.api;

import android.content.Context;

import com.bzh.sportrecord.model.Book;

import org.json.JSONObject;

import io.reactivex.Observable;
import retrofit2.Response;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/14 14:14
 */
public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager() {
        this.mRetrofitService = RetrofitHelper.getInstance().getServer();
    }

    //测试
    public Observable<Book> getSearchBooks(String name, String tag, int start, int count) {
        return mRetrofitService.getSearchBook(name, tag, start, count);
    }

    //登陆
    public Observable<JSONObject> login(String username, String password) {
        return mRetrofitService.login(username,password);
    }


}
