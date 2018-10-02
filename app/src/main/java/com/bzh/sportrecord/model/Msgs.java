package com.bzh.sportrecord.model;

/**
 * @author 毕泽浩
 * @Description: 存放未处理的消息
 * @time 2018/10/2 9:24
 */
public class Msgs {
    String username;
    String message;

    public Msgs(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
