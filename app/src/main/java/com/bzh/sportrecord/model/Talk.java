package com.bzh.sportrecord.model;

import java.util.Date;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/28 14:06
 */
public class Talk {
    private String code;
    private Long id;
    private String sender;
    private String receiver;
    private Date time;
    private String message;

    public Talk() {
    }

    public Talk(String code, Long id, String sender, String receiver, Date time, String message) {
        this.code = code;
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Talk{" +
                "code='" + code + '\'' +
                ", id=" + id +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", time=" + time +
                ", message='" + message + '\'' +
                '}';
    }
}
