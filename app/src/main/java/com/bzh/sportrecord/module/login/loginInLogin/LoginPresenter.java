package com.bzh.sportrecord.module.login.loginInLogin;

import android.content.Context;
import android.content.Intent;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.model.ApiLogin;
import com.bzh.sportrecord.module.home.HomeActivity;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 11:00
 */
public class LoginPresenter implements LoginContract.Presenter {

    private Context mContext;

    private LoginContract.View mView;

    public LoginPresenter(Context context, LoginContract.View view) {
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
        System.out.println(username);
        System.out.println(password);
        DataManager dataManager = DataManager.getInstance();
        Observable<ApiLogin> observable = dataManager.login(username, password);
        dataManager.successHandler(observable, new DataManager.callBack() {
            @Override
            public <T> void run(T t) {
                ApiLogin apiLogin = (ApiLogin) t;
                System.out.println("登陆成功,跳转页面");
                System.out.println("保存用户信息");
                dataManager.setHeadValue(apiLogin.getData().getX_Auth_Token()); //添加 token
                App.loginSign = true; //设置登陆状态为true
                App.id = apiLogin.getMessage();
                Intent intent = new Intent(mContext, HomeActivity.class);
                mContext.startActivity(intent); //跳转到home页面
                mView.shutDownLoading();
            }
        });
    }
}
