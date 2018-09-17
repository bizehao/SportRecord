package com.bzh.sportrecord.di.module;

import android.content.Context;

import com.bzh.sportrecord.di.qualifier.ApplicationScoped;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private Context context;

    public AppModule(Context context){
        this.context = context;
    }

    //@ApplicationScoped
    @Provides
    public Context providesContext(){
        return this.context;
    }

}
