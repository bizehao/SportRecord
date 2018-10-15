package com.bzh.sportrecord;

import android.app.Application;

import com.bzh.sportrecord.di.component.AppComponent;
import com.bzh.sportrecord.di.component.DaggerAppComponent;
import com.bzh.sportrecord.di.module.AppModule;
import com.bzh.sportrecord.greenDao.DaoMaster;
import com.bzh.sportrecord.greenDao.DaoSession;
import com.bzh.sportrecord.model.Talk;
import com.bzh.sportrecord.module.talk.WebSocketChatClient;

import org.greenrobot.greendao.database.Database;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class App extends Application {

    public static AppComponent appComponent;

    private static WebSocketChatClient webSocketChatClient;

    private static DaoSession daoSession;

    //用户
    private static User user;

    private static List<Talk> list1 = new ArrayList<>(); //系统通知 100
    private static List<Talk> list2 = new ArrayList<>(); //会话消息 200
    private static List<Talk> list3 = new ArrayList<>(); //other 300
    //消息集合
    private static Map<String, List<Talk>> map;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();

        user = new User();

        map = new HashMap<>();
        map.put("100", list1);
        map.put("200", list2);
        map.put("300", list3);
        init();
    }

    public static void connectWS(){
        try {
            webSocketChatClient = new WebSocketChatClient(new URI("ws://192.168.1.196:8080"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        webSocketChatClient.connect();
    }

    //获取数据库操作
    public static DaoSession getDaoSession() {
        return daoSession;
    }

    //初始化greenDao
    public void init() {
        //初始化数据库
        DaoMaster.DevOpenHelper openHelper = new DaoMaster.DevOpenHelper(this, "DATA_SportRecord");
        Database db = openHelper.getWritableDb();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    //获取消息集合
    public static Map<String, List<Talk>> getMap() {
        return map;
    }

    //获取webSocket连接
    public static WebSocketChatClient getWebSocket() { //获取webSocket连接
        return webSocketChatClient;
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

    //获取是否需要验证
    public static boolean getWhetherVerify() {
        return user.whetherVerify;
    }
    //设置验证状态
    public static void setWhetherVerify(boolean verify) {
        user.setWhetherVerify(verify);
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
        private boolean whetherVerify = true;
        private String username;
        private String img;
        private String token;

        private void setLoginSign(boolean loginSign) {
            this.loginSign = loginSign;
        }

        public void setWhetherVerify(boolean whetherVerify) {
            this.whetherVerify = whetherVerify;
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
