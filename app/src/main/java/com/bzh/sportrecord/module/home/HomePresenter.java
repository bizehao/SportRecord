package com.bzh.sportrecord.module.home;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bzh.sportrecord.App;
import com.bzh.sportrecord.api.DataManager;
import com.bzh.sportrecord.greenDao.DaoSession;
import com.bzh.sportrecord.greenModel.FriendsInfo;
import com.bzh.sportrecord.model.ApiCommon;
import com.bzh.sportrecord.model.ApiFriends;
import com.bzh.sportrecord.model.ApiUserInfo;
import com.bzh.sportrecord.module.login.LoginActivity;
import com.bzh.sportrecord.utils.AppManager;

import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class HomePresenter implements HomeContract.Presenter {

    private static final String TAG = "HomePresenter";

    private Context mContext;

    private HomeContract.View mView;

    public HomePresenter(Context context, HomeContract.View view) {
        this.mContext = context;
        this.mView = view;
    }

    @Override
    public void loadData(String username) {
        DataManager dataManager = DataManager.getInstance();
        Observable<ApiUserInfo> observable = dataManager.getUserInfo(username);
        dataManager.successHandler(observable, new DataManager.callBack() { //获取该用户的个人信息
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public <T> void run(T t) {
                ApiUserInfo apiUserInfo = (ApiUserInfo) t;
                //解码
                Base64.Decoder decoder = Base64.getDecoder();
                byte[] bytes = decoder.decode(apiUserInfo.getData().getHeadportrait());
                Bitmap bitMap = BitmapFactory.decodeStream(new ByteArrayInputStream(bytes));
                App.setImg(apiUserInfo.getData().getHeadportrait());
                mView.setHeadPortrait(bitMap);
                mView.setHeadName(apiUserInfo.getData().getName());
                mView.setHeadMotto(apiUserInfo.getData().getMotto());
                App.connectWS(); //连接 webSocket;
                System.out.println("webSocket连接成功");
            }
        });
        Observable<ApiFriends> friends = dataManager.getFriends(username);
        dataManager.successHandler(friends, new DataManager.callBack() { //缓存该用户的好友信息
            @Override
            public <T> void run(T t) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        ApiFriends apiUserInfo = (ApiFriends) t;
                        DaoSession daoSession = App.getDaoSession();
                        daoSession.getFriendsInfoDao().deleteAll();
                        //daoSession.getMessageInfoDao().deleteAll();
                        List<ApiFriends.DataBean> list = apiUserInfo.getData();
                        FriendsInfo info;
                        for (ApiFriends.DataBean item : list) {
                            info = new FriendsInfo();
                            info.setId(null);
                            info.setUsername(item.getUsername());
                            info.setName(item.getName());
                            info.setAddress(item.getAddress());
                            info.setDescript(item.getDescript());
                            info.setHeadportrait(item.getHeadportrait());
                            info.setMotto(item.getMotto());
                            info.setRemarkname(item.getRemarkName());
                            daoSession.getFriendsInfoDao().insert(info);
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void loginOut(String username) {
        DataManager dataManager = DataManager.getInstance();
        dataManager.successHandler(dataManager.loginOut(username), new DataManager.callBack() {
            @Override
            public <T> void run(T t) {
                ApiCommon apiCommon = (ApiCommon) t;
                if((boolean)apiCommon.getData()){
                    mView.failSettring();
                    App.getWebSocket().close();
                    LoginActivity.open(mContext);
                    AppManager.getAppManager().finishActivity((Activity) mContext);
                }
            }
        });
    }
}
