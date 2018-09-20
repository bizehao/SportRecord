package com.bzh.sportrecord.module.login.loginInRegister;

import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;

import java.util.Map;

public interface RegisterContract {

    interface View extends BaseView{

        //获取所有用户信息
        Map<String, String> getAllUserInfo();

    }

    interface Presenter extends BasePresenter<RegisterContract.View>{

    }
}
