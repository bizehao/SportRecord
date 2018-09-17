package com.bzh.sportrecord.di.component;

import android.content.Context;
import com.bzh.sportrecord.di.module.AppModule;
import com.bzh.sportrecord.di.qualifier.ApplicationScoped;

import dagger.Component;

//@ApplicationScoped
@Component(modules = {AppModule.class})
public interface AppComponent {

    // 向下层提供Context
    Context getContext();

}
