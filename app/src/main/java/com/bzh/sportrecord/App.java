package com.bzh.sportrecord;

import android.app.Application;
import com.bzh.sportrecord.di.component.AppComponent;
import com.bzh.sportrecord.di.component.DaggerAppComponent;
import com.bzh.sportrecord.di.module.AppModule;

public class App extends Application {

    public static AppComponent appComponent;

    //用户
    private static final User user = new User();

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
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
