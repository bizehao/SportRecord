package com.bzh.sportrecord.module.home;

import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;

public interface HomeContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
        /**
         * 设置登录状态
         *
         * @param loginStatus login status
         */
        void setLoginStatus(boolean loginStatus);

        /**
         * 获取登录状态
         *
         * @return if is login status
         */
        boolean getLoginStatus();

        /**
         * 获取账号
         *
         * @return login account
         */
        String getLoginAccount();

        /**
         * 设置登录账号
         *
         * @param account account
         */
        void setLoginAccount(String account);

        /**
         * 设置登录密码
         *
         * @param password password
         */
        void setLoginPassword(String password);
    }
}
