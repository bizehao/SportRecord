package com.bzh.sportrecord.module.talk.talkFriends;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.data.model.FriendsInfo;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/20 12:20
 */
public class FriendsViewModel extends ViewModel {

    private LiveData<List<FriendsInfo>> friends; //初始

    private MutableLiveData<List<FriendsInfo>> screenFriends; //筛选

    public FriendsViewModel() {
        this.friends = AppDatabase.getAppDatabase().friendsInfoDao().getAll();
    }

    public LiveData<List<FriendsInfo>> getFriends() {
        return friends;
    }

    public MutableLiveData<List<FriendsInfo>> getScreenFriends() {
        if(screenFriends == null){
            screenFriends = new MutableLiveData<>();
        }
        return screenFriends;
    }

    public void setScreenFriends(List<FriendsInfo> screenFriends) {
        this.screenFriends.setValue(screenFriends);
    }
}
