package com.bzh.sportrecord.module.home;

import android.content.Context;

import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.model.ApiUserInfo;
import com.bzh.sportrecord.module.login.loginInLogin.LoginContract;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter implements HomeContract.Presenter {

    private Context mContext;

    private HomeContract.View mView;

    public HomePresenter(Context context, HomeContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void attachView(HomeContract.View view) {

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
    public void loadData(String id) {
        DataManager dataManager = DataManager.getInstance();
        Observable<ApiUserInfo> observable =  dataManager.getUserInfo(id);
        dataManager.successHandler(observable, new DataManager.callBack() {
            @Override
            public <T> void run(T t) {
                ApiUserInfo apiUserInfo = (ApiUserInfo) t;
                System.out.println(apiUserInfo);
                mView.setHeadPortrait(1); //头像 暂未处理
                mView.setHeadName(apiUserInfo.getData().getName());
                mView.setHeadMotto(apiUserInfo.getData().getMotto());
            }
        });
    }
}
