package com.bzh.sportrecord.module.home;

import android.content.Context;
import android.util.Log;
import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.model.ApiUserInfo;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

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
    public void loadData(String username) {
        Log.d(TAG, "loadData: "+username);
        DataManager dataManager = DataManager.getInstance();
        Observable<ApiUserInfo> observable =  dataManager.getUserInfo(username);
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
