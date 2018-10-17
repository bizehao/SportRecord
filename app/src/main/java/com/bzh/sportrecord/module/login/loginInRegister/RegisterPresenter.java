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
    public void register() {
        Map<String, String>  map = mView.getAllUserInfo();
        DataManager dataManager = DataManager.getInstance();
        Observable<ApiRegister> observable = dataManager.register(map);
        dataManager.successHandler(observable, new DataManager.callBack() {
            @Override
            public <T> void run(T t) {
                ApiRegister apiRegister = (ApiRegister) t;
                if(apiRegister.getData() == 1){
                    mView.showNormal();
                }else if(apiRegister.getData() == 0){
                    mView.showError();
                }else {
                    mView.showErrorMsg("未知错误，请稍后再试");
                }
            }
        });
    }
}
