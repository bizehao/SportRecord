package com.bzh.sportrecord.di.module;

import com.bzh.sportrecord.module.home.HomeContract;
import com.bzh.sportrecord.module.home.HomePresenter;
import com.bzh.sportrecord.module.home.homePlan.TalkFragment;
import com.bzh.sportrecord.module.home.homePlan.TalkModule;
import com.bzh.sportrecord.module.login.loginInLogin.LoginContract;
import com.bzh.sportrecord.module.login.loginInLogin.LoginFragment;
import com.bzh.sportrecord.module.login.loginInLogin.LoginPresenter;
import com.bzh.sportrecord.module.login.loginInRegister.RegisterContract;
import com.bzh.sportrecord.module.login.loginInRegister.RegisterFragment;
import com.bzh.sportrecord.module.login.loginInRegister.RegisterPresenter;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/23 9:30
 */
@Module
public abstract class LoginModule {

    //登录
    @ContributesAndroidInjector
    abstract LoginFragment providesLoginFragment();

    //注册
    @ContributesAndroidInjector
    abstract RegisterFragment providesRegisterFragment();

    @Binds
    abstract LoginContract.Presenter loginPresenter(LoginPresenter presenter);

    @Binds
    abstract RegisterContract.Presenter registerPresenter(RegisterPresenter presenter);
}
