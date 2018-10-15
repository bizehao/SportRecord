package com.bzh.sportrecord.di.module;

import android.content.Context;

import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;
import com.bzh.sportrecord.module.home.homePlan.PlanContract;
import com.bzh.sportrecord.module.home.homePlan.PlanPresenter;
import com.bzh.sportrecord.module.login.loginInLogin.LoginContract;
import com.bzh.sportrecord.module.login.loginInLogin.LoginPresenter;
import com.bzh.sportrecord.module.login.loginInRegister.RegisterContract;
import com.bzh.sportrecord.module.login.loginInRegister.RegisterPresenter;

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

    //登录
    @Provides
    LoginContract.Presenter providesLoginPresenter(){
        return new LoginPresenter(context, (LoginContract.View) baseView);
    }

    //注册
    @Provides
    RegisterContract.Presenter providesRegisterPresenter(){
        return new RegisterPresenter(context, (RegisterContract.View) baseView);
    }

    //通信
    @Provides
    PlanContract.Presenter providesPlanPresenter(){
        return new PlanPresenter(context, (PlanContract.View) baseView);
    }
}
