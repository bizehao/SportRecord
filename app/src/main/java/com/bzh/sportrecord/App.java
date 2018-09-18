package com.bzh.sportrecord;

import android.app.Application;
import com.bzh.sportrecord.di.component.AppComponent;
import com.bzh.sportrecord.di.component.DaggerAppComponent;
import com.bzh.sportrecord.di.module.AppModule;

public class App extends Application {

    public static AppComponent appComponent;

    //用户的登陆状态
    public static boolean loginSign = false;
    //用户的id
    public static String id;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
