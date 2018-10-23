package com.bzh.sportrecord.di.module;

import com.bzh.sportrecord.module.home.HomeContract;
import com.bzh.sportrecord.module.home.HomePresenter;
import com.bzh.sportrecord.module.home.homeNews.NewsFragment;
import com.bzh.sportrecord.module.home.homePlan.TalkFragment;
import com.bzh.sportrecord.module.home.homeSport.SportFragment;

import dagger.Binds;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/23 9:27
 */
@Module
public abstract class HomeModule {

    @ContributesAndroidInjector
    abstract NewsFragment providesNewsFragment();

    @ContributesAndroidInjector
    abstract TalkFragment providesTalkFragment();

    @ContributesAndroidInjector
    abstract SportFragment providesSportFragment();

    @Binds
    abstract HomeContract.Presenter homePresenter(HomePresenter presenter);
}
