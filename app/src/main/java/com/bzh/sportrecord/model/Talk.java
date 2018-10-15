package com.bzh.sportrecord.model;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/28 14:06
 */
public class Talk {
    private String code;
    private String sender;
    private String receiver;
    private String time;
    private String message;

    public Talk() {
    }

    public Talk(String code, String sender, String receiver, String time, String message) {
        this.code = code;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
