package com.bzh.sportrecord.module.login.loginInRegister;

import android.content.Context;

import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.model.ApiRegister;
import com.bzh.sportrecord.model.ApiUserInfo;
import com.bzh.sportrecord.module.login.loginInLogin.LoginContract;

import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class RegisterPresenter implements RegisterContract.Presenter {

    private Context mContext;

    private RegisterContract.View mView;

    public RegisterPresenter(Context context, RegisterContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void attachView(RegisterContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public void addRxBindingSubscribe(Disposable disposable) {

    }

    @Override
    public int getCurrentPage() {
        return 0;
    }

    @Override
    public void register() {
        Map<String, String>  map = mView.getAllUserInfo();
        DataManager dataManager = DataManager.getInstance();
        Observable<ApiRegister> observable = dataManager.register(map);
        dataManager.successHandler(observable, new DataManager.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRegister apiRegister = (ApiRegister) t;
                System.out.println(apiRegister.toString());
            }
        });
    }
}
