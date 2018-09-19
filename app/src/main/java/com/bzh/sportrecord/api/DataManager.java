package com.bzh.sportrecord.api;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.model.ApiLogin;
import com.bzh.sportrecord.model.ApiUserInfo;
import com.bzh.sportrecord.model.Book;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/14 14:14
 */
public class DataManager implements RetrofitService {

    private static DataManager dataManager;

    private RetrofitService mRetrofitService;

    protected static String headValue;

    private DataManager() {
        this.mRetrofitService = RetrofitHelper.getInstance().getServer();
    }

    public void setHeadValue(String headValue) {
        DataManager.headValue = headValue;
    }

    public static DataManager getInstance() {
        if (dataManager == null) {
            dataManager = new DataManager();
        }
        return dataManager;
    }

    public <T> void successHandler(Observable<T> observable, callBack callBack){
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> {
                    callBack.run(t);
                }, throwable -> {
                    System.out.println("网络请求发生错误");
                    throwable.getMessage();
                });
    }

    public interface callBack{
        <T> void run(T t);
    }

    @Override
    public Observable<Book> getSearchBook(String name, String tag, int start, int count) {
        return mRetrofitService.getSearchBook(name, tag, start, count);
    }

    @Override
    public Observable<ApiLogin> login(String username, String password) {
        this.setHeadValue(null);
        return mRetrofitService.login(username, password);
    }

    @Override
    public Observable<ApiUserInfo> getUserInfo(String id) {
        return mRetrofitService.getUserInfo(id);
    }


}
