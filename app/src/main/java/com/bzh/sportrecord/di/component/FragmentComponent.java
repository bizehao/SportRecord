package com.bzh.sportrecord.di.component;

import com.bzh.sportrecord.base.fragment.BaseFragment;
import com.bzh.sportrecord.base.view.BaseView;
import com.bzh.sportrecord.di.module.ActivityModule;
import com.bzh.sportrecord.di.module.FragmentModule;
import com.bzh.sportrecord.module.home.HomeActivity;
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

    void inject(LoginFragment loginFragment);

    void inject(RegisterFragment registerFragment);
}
