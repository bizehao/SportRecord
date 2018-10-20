package com.bzh.sportrecord.di.component;

import com.bzh.sportrecord.di.module.FragmentModule;
import com.bzh.sportrecord.module.home.homePlan.TalkFragment;
import com.bzh.sportrecord.module.login.loginInLogin.LoginFragment;
import com.bzh.sportrecord.module.login.loginInRegister.RegisterFragment;

import dagger.Component;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/17 12:59
 */
@Component(modules = {FragmentModule.class})
public interface FragmentComponent {

    //登录
    void inject(LoginFragment loginFragment);

    //注册
    void inject(RegisterFragment registerFragment);

    //通信
    void inject(TalkFragment messageFragment);
}
