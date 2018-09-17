package com.bzh.sportrecord.di.component;

import android.app.Activity;

import com.bzh.sportrecord.MainActivity;
import com.bzh.sportrecord.di.module.ActivityModule;
import com.bzh.sportrecord.module.home.HomeActivity;
import com.bzh.sportrecord.module.home.HomeContract;

import dagger.Component;

@Component(modules = {ActivityModule.class}, dependencies = {AppComponent.class})
public interface ActivityComponent {

    void inject(MainActivity activity);

    void inject(HomeActivity activity);

}
