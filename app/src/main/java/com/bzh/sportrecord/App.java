package com.bzh.sportrecord;

import android.app.Application;
import android.content.Context;

import com.bzh.sportrecord.di.component.AppComponent;
import com.bzh.sportrecord.di.component.DaggerAppComponent;
import com.bzh.sportrecord.di.module.AppModule;
import com.bzh.sportrecord.model.Msgs;
import com.bzh.sportrecord.module.home.homeNews.NewsFragment;
import com.bzh.sportrecord.module.home.homeSport.SportFragment;
import com.bzh.sportrecord.module.home.homeSport.WebSocketChatClient;
import com.bzh.sportrecord.module.talk.model.User;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.Subject;

public class App extends Application {

    public static AppComponent appComponent;

    private static WebSocketChatClient webSocketChatClient;

    //用户
    private static User user;

    private static List<Msgs> list1 = new ArrayList<>(); //系统通知 100
    private static List<Msgs> list2 = new ArrayList<>(); //会话消息 200
    private static List<Msgs> list3 = new ArrayList<>(); //other 300
    //消息集合
    private static Map<String,List<Msgs>> map;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        user = new User();

        try {
            webSocketChatClient = new WebSocketChatClient(new URI("ws://192.168.31.75:8080"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketChatClient.connect();
        map = new HashMap<>();
        map.put("100",list1);
        map.put("200",list2);
        map.put("300",list3);
    }

    public static Map<String, List<Msgs>> getMap() {
        return map;
    }

    public static WebSocketChatClient getWebSocket(){ //获取webSocket连接
        return webSocketChatClient;
    }

    public static void setUser(boolean loginSign,String username,String token){
        user.setLoginSign(loginSign);
        user.setUsername(username);
        user.setToken(token);
    }

    public static void setLoginSign(boolean loginSign){
        if(!loginSign){
            user.setLoginSign(false);
            user.setUsername(null);
            user.setToken(null);
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

    private static class User{
        private boolean loginSign = false;
        private String username;
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

        @Override
        public String toString() {
            return "User{" +
                    "loginSign=" + loginSign +
                    ", username='" + username + '\'' +
                    ", token='" + token + '\'' +
                    '}';
        }
    }
}
