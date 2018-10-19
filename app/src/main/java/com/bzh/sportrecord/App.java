package com.bzh.sportrecord;

import android.app.Application;
import android.arch.persistence.room.Room;
import com.bzh.sportrecord.data.AppDatabase;
import com.bzh.sportrecord.di.component.AppComponent;
import com.bzh.sportrecord.di.component.DaggerAppComponent;
import com.bzh.sportrecord.di.module.AppModule;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.net.URI;
import java.net.URISyntaxException;

import timber.log.Timber;

public class App extends Application {

    public static final String ip = "192.168.1.196";//172.26.220.193  192.168.31.75  192.168.1.196

    public static AppComponent appComponent;

    private static WebSocketChatClient webSocketChatClient;

    private static Gson gson;
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

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        user = new User();
    }

    /**
     * 获取gson实例
     *
     * @return
     */
    public static Gson getGsonInstance() {
        if (gson == null) {
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
        }
        return gson;
    }

    public static void connectWS() {
        try {
            webSocketChatClient = new WebSocketChatClient(new URI("ws://" + App.ip + ":8080"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketChatClient.connect();
    }

    //获取webSocket连接
    public static WebSocketChatClient getWebSocket() { //获取webSocket连接
        return webSocketChatClient;
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
     * @param loginSign
     * @param username
     * @param token
     */
    public static void setUser(boolean loginSign, String username, String token) {
        user.setLoginSign(loginSign);
        user.setUsername(username);
        user.setToken(token);
    }

    public static void setLoginSign(boolean loginSign) {
        if (!loginSign) {
            user.setLoginSign(false);
            user.setUsername(null);
            user.setToken(null);
            user.setImg(null);
        }
    }

    public static boolean getLoginSign() {
        return user.loginSign;
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
        private boolean loginSign = false;
        private String username;
        private String img;
        private String token;

        private void setLoginSign(boolean loginSign) {
            this.loginSign = loginSign;
        }

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
}
