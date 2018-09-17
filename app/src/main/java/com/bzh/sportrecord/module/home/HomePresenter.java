package com.bzh.sportrecord.module.home;

import io.reactivex.disposables.Disposable;

public class HomePresenter implements HomeContract.Presenter {
    @Override
    public void setLoginStatus(boolean loginStatus) {

    }

    @Override
    public boolean getLoginStatus() {
        return false;
    }

    @Override
    public String getLoginAccount() {
        return null;
    }

    @Override
    public void setLoginAccount(String account) {

    }

    @Override
    public void setLoginPassword(String password) {

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
}
