package com.bzh.sportrecord.module.home.homePlan;

import com.bzh.sportrecord.base.presenter.BasePresenter;
import com.bzh.sportrecord.base.view.BaseView;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/15 13:24
 */
public interface PlanContract {

    interface View extends BaseView {

    }

    interface Presenter extends BasePresenter<View> {
       /* //获取未读消息
        List<MessageInfo> getMessageInfo();

        //获取好友信息
        FriendsInfo getFriendsInfo(String friendName);*/
    }
}
