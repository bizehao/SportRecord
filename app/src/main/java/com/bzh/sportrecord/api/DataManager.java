package com.bzh.sportrecord.api;

import android.content.Context;

import com.bzh.sportrecord.model.Book;

import io.reactivex.Observable;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/14 14:14
 */
public class DataManager {
    private RetrofitService mRetrofitService;

    public DataManager(Context context) {
        this.mRetrofitService = RetrofitHelper.getInstance(context).getServer();
    }

    public Observable<Book> getSearchBooks(String name, String tag, int start, int count) {
        return mRetrofitService.getSearchBook(name, tag, start, count);
    }
}
