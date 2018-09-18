package com.bzh.sportrecord.di.module;

import android.content.Context;
import com.bzh.sportrecord.base.view.BaseView;
import com.bzh.sportrecord.module.home.HomeContract;
import com.bzh.sportrecord.module.home.HomePresenter;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private Context context;
    private BaseView baseView;

    public ActivityModule(Context context, BaseView baseView){
        this.context = context;
        this.baseView = baseView;
    }

    @Provides
    HomePresenter providesHomePresenter(){
        return new HomePresenter(context, (HomeContract.View) baseView);
    }
}
