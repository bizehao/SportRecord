package com.bzh.sportrecord.di.module.TwoGrade.ThreeGrade;

import com.bzh.sportrecord.module.home.homeSport.SportCensusFragment;
import com.bzh.sportrecord.module.home.homeSport.SportMainFragment;
import com.bzh.sportrecord.module.home.homeSport.SportRatioFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/25 13:16
 */
@Module
public abstract class SportModule {

    @ContributesAndroidInjector
    abstract SportCensusFragment providesSubSportCensusFragment();

    @ContributesAndroidInjector
    abstract SportMainFragment providesSubSportMainFragment();

    @ContributesAndroidInjector
    abstract SportRatioFragment providesSubSportRatioFragment();

}
