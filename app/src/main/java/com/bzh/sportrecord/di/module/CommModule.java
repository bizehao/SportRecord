package com.bzh.sportrecord.di.module;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.bzh.sportrecord.App;
import com.bzh.sportrecord.MainAttrs;
import com.bzh.sportrecord.api.RetrofitHelper;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.net.URI;
import java.net.URISyntaxException;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/22 22:49
 */
@Module
public abstract class CommModule {

    @Singleton
    @Binds
    public abstract Context context(App application);

    @Singleton
    @Provides
    public static Gson providerGson() { //gson
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    @Singleton
    @Provides
    public static MainAttrs providerMainAttrs() { //主属性
        return new MainAttrs();
    }

    @Singleton
    @Provides
    public static WebSocketChatClient providerWebSocketChatClient(Gson gson, MainAttrs mainAttrs) {
        try {
            return new WebSocketChatClient(new URI("ws://"+App.ip+":8080"), gson, mainAttrs);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Singleton
    @Provides
    public static RetrofitHelper providerRetrofitHelper(MainAttrs mainAttrs) {
        return new RetrofitHelper(mainAttrs);
    }

    @Singleton
    @Provides
    public static SharedPreferences providerSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }
}
