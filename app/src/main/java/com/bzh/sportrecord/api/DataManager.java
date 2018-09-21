package com.bzh.sportrecord.api;

import com.bzh.sportrecord.model.ApiLogin;
import com.bzh.sportrecord.model.ApiRegister;
import com.bzh.sportrecord.model.ApiUserInfo;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/14 14:14
 */
public class DataManager implements RetrofitService {

    private static DataManager dataManager;

    private RetrofitService mRetrofitService;

    private DataManager() {
        this.mRetrofitService = RetrofitHelper.getInstance().getServer();
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
                    throwable.printStackTrace();
                    throwable.getMessage();
                    System.out.println("网络请求发生错误");
                });
    }

    public interface callBack{
        <T> void run(T t);
    }

    @Override
    public Observable<ApiLogin> login(String username, String password) {
        return mRetrofitService.login(username, password);
    }

    @Override
    public Observable<ApiUserInfo> getUserInfo(String username) {
        return mRetrofitService.getUserInfo(username);
    }

    @Override
    public Observable<ApiRegister> register(Map<String, String> requestRegister) {
        return mRetrofitService.register(requestRegister);
    }


}
