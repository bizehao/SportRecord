package com.bzh.sportrecord.module.home.homePlan;

import android.content.Context;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/15 13:26
 */
public class PlanPresenter implements PlanContract.Presenter {

    private Context mContext;
    private PlanContract.View mView;

    public PlanPresenter(Context context, PlanContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    /*@Override
    public List<MessageInfo> getMessageInfo() {
        return MessageInfoHandler.selectByCondition(
                MessageInfoDao.Properties.Receiver.eq(App.getUsername()),
                MessageInfoDao.Properties.ReadSign.eq(false));
    }

    @Override
    public FriendsInfo getFriendsInfo(String friendName) {
        return FriendsInfoHandler.selectByCondition(FriendsInfoDao.Properties.Username.eq(friendName));
    }*/
}
