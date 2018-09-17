package com.bzh.sportrecord.di.module;

import android.content.Context;

import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;
import com.bzh.sportrecord.module.login.loginInLogin.LoginContract;
import com.bzh.sportrecord.module.login.loginInLogin.LoginPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 13:00
 */
@Module
public class FragmentModule {

    private Context context;
    private BaseView baseView;

    public FragmentModule(Context context, BaseView baseView){
        this.context = context;
        this.baseView = baseView;
    }

    @Provides
    LoginPresenter providesBasePresenter(){
        return new LoginPresenter(context, (LoginContract.View) baseView);
    }
}
