package com.bzh.sportrecord.module.home.homePlan;

import android.content.Context;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.greenDao.DaoSession;
import com.bzh.sportrecord.greenDao.FriendsInfoDao;
import com.bzh.sportrecord.greenDao.MessageInfoDao;
import com.bzh.sportrecord.greenModel.FriendsInfo;
import com.bzh.sportrecord.greenModel.MessageInfo;

import java.util.List;

import io.reactivex.disposables.Disposable;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/15 13:26
 */
public class PlanPresenter implements PlanContract.Presenter {

    private Context mContext;
    private PlanContract.View mView;
    private DaoSession daoSession;

    public PlanPresenter(Context context, PlanContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void attachView(PlanContract.View view) {

    }

    @Override
    public void detachView() {

    }

    @Override
    public int getCurrentPage() {
        return 0;
    }

    @Override
    public List<MessageInfo> getMessageInfo() {
        daoSession = App.getDaoSession();
        MessageInfoDao messageInfoDao = daoSession.getMessageInfoDao();
        List<MessageInfo> messageInfos = messageInfoDao.queryBuilder()
                .where(MessageInfoDao.Properties.Username.eq(App.getUsername()), MessageInfoDao.Properties.ReadSign.eq(false))
                .list();
        return messageInfos;
    }

    @Override
    public FriendsInfo getFriendsInfo(String friendName) {
        FriendsInfoDao friendsInfoDao = daoSession.getFriendsInfoDao();
        FriendsInfo friendsInfo = friendsInfoDao.queryBuilder().where(FriendsInfoDao.Properties.Username.eq(friendName)).unique();
        return friendsInfo;
    }
}
