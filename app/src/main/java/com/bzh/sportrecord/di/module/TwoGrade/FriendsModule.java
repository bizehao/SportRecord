package com.bzh.sportrecord.di.module.TwoGrade;

import com.bzh.sportrecord.module.home.homeNews.NewsFragment;
import com.bzh.sportrecord.module.home.homePlan.TalkFragment;
import com.bzh.sportrecord.module.talk.talkFriends.FriendsDiaFrag;
import com.bzh.sportrecord.module.talk.talkFriends.RedFriendDiaFrag;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/23 15:07
 */
@Module
public abstract class FriendsModule {

    @ContributesAndroidInjector
    abstract RedFriendDiaFrag providesRedFriendDiaFrag();

    @ContributesAndroidInjector
    abstract FriendsDiaFrag providesFriendsDiaFrag();
}
