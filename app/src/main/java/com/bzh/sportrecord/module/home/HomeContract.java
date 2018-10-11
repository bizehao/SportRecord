package com.bzh.sportrecord.module.home;

import android.graphics.Bitmap;

import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;

public interface HomeContract {

    interface View extends BaseView {
        /**
         * 设置头像
         *
         * @param bitmap
         */
        void setHeadPortrait(Bitmap bitmap);

        /**
         * 设置名称
         *
         * @param name
         */
        void setHeadName(String name);

        /**
         * 设置格言
         *
         * @param motto
         */
        void setHeadMotto(String motto);
    }

    interface Presenter extends BasePresenter<View> {

        /**
         * 加载数据
         * @param username
         */
        void loadData(String username);
    }
}
