package com.bzh.sportrecord.di.component;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.di.module.ActivityModule;
import com.bzh.sportrecord.di.module.CommModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

@Singleton
@Component(modules = {AndroidSupportInjectionModule.class,
        CommModule.class,
        ActivityModule.class})
public interface AppComponent extends AndroidInjector<App> {

    @Component.Builder
    abstract class Builder extends AndroidInjector.Builder<App> {
    }

}
