package com.bzh.sportrecord;

import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.di.component.DaggerAppComponent;
import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;
import timber.log.Timber;

public class App extends DaggerApplication {

    public static final String ip = "192.168.1.196";//172.26.220.193  192.168.31.75  192.168.1.196

    //用户
    private static User user;

    private static String friend; //当前会话的朋友

    @Override
    public void onCreate() {
        super.onCreate();
        /**
         * 仅在Debug时初始化Timber
         */
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        user = new User();

        AppDatabase.initAppDatabase(this); //初始化数据库

    }

    //设置当前会话的朋友
    public static void setFriend(String friend) {
        App.friend = friend;
    }

    //获取当前会话的朋友
    public static String getFriend() {
        return friend;
    }

    /**
     * 设置用户信息
     *
     * @param username
     * @param token
     */
    public static void setUser(String username, String token) {
        user.setUsername(username);
        user.setToken(token);
    }

    public static void setLoginSign(boolean loginSign) {
        if (!loginSign) {
            user.setUsername(null);
            user.setToken(null);
            user.setImg(null);
        }
    }

    public static String getUsername() {
        return user.username;
    }

    public static String getToken() {
        return user.token;
    }

    public static String getImg() {
        return user.img;
    }

    public static void setImg(String img) {
        user.img = img;
    }

    private static class User {
        private String username;
        private String img;
        private String token;

        private void setUsername(String username) {
            this.username = username;
        }

        private void setToken(String token) {
            this.token = token;
        }

        public void setImg(String img) {
            this.img = img;
        }

    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        return DaggerAppComponent.builder().create(this);
    }
}
