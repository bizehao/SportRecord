package com.bzh.sportrecord.di.module;

import android.app.Activity;

import com.bzh.sportrecord.MainActivity;
import com.bzh.sportrecord.module.home.HomeActivity;
import com.bzh.sportrecord.module.login.LoginActivity;
import com.bzh.sportrecord.module.talk.talkFriends.FriendsActivity;
import com.bzh.sportrecord.module.talk.talkFriends.RedFriendDiaFrag;
import com.bzh.sportrecord.module.talk.talkMessage.MessageActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {

    //主页
    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeActivity providesHomeActivity();

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity providesLoginActivity();

    @ContributesAndroidInjector(modules = FriendsModule.class)
    abstract FriendsActivity providesFriendsActivity();

    @ContributesAndroidInjector
    abstract MessageActivity providesMessageActivity();

    @ContributesAndroidInjector
    abstract MainActivity providesMainActivity();
}
