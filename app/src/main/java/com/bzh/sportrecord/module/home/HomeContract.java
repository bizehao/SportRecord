package com.bzh.sportrecord.module.home;

import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;

public interface HomeContract {

    interface View extends BaseView {
        /**
         * 设置头像
         *
         * @param image
         */
        void setHeadPortrait(int image);

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
         * @param id
         */
        void loadData(String id);
    }
}
