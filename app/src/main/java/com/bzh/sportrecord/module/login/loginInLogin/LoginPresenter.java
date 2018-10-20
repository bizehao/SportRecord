package com.bzh.sportrecord.module.login.loginInLogin;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.model.ApiLogin;
import com.bzh.sportrecord.module.home.HomeActivity;
import com.bzh.sportrecord.module.login.LoginActivity;

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

    private static final String TAG = "LoginPresenter";

    private Context mContext;

    private LoginContract.View mView;

    public LoginPresenter(Context context, LoginContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void login() {
        mView.showLoading();
        String username = mView.getUsername();
        String password = mView.getPassword();
        DataManager dataManager = DataManager.getInstance();
        Observable<ApiLogin> observable = dataManager.login(username, password);
        dataManager.successHandler(observable, new DataManager.callBack() {
            @Override
            public <T> void run(T t) {
                ApiLogin apiLogin = (ApiLogin) t;
                if(apiLogin.getCode().equals("200")){
                    App.getMainAttrs().setLoginSign(true);
                    App.setUser(apiLogin.getData().getUsername(),apiLogin.getData().getX_Auth_Token());
                    HomeActivity.open(mContext);
                    mView.shutDownLoading();
                    LoginActivity loginActivity = (LoginActivity) mContext;
                    loginActivity.finish();
                }else {
                    mView.showErrorMsg("登录失败");
                }
            }
        });
    }
}
