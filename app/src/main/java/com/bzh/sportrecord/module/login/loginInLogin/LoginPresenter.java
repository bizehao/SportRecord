package com.bzh.sportrecord.module.login.loginInLogin;

import android.content.Context;

import io.reactivex.disposables.Disposable;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 11:00
 */
public class LoginPresenter implements LoginContract.Presenter {

    private Context mContext;

    private LoginContract.View mView;

    public LoginPresenter(Context context,LoginContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void attachView(LoginContract.View view) {

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
    public void login() {
        mView.showLoading();
        String username = mView.getUsername();
        String password = mView.getPassword();

        System.out.println("你好啊");
        System.out.println(username);
        System.out.println(password);
        mView.shutDownLoading();
    }
}
