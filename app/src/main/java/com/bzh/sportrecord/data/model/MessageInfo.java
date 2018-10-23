package com.bzh.sportrecord.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.icu.text.SimpleDateFormat;

import com.bzh.sportrecord.model.Talk;

import java.util.Date;

/**
 * @author 毕泽浩
 * @Description: 消息表
 * @time 2018/10/19 11:21
 */
@Entity
public class MessageInfo {

    @PrimaryKey
    private Long id; //id
    private String sender; //发送者
    private String receiver; //接受者
    private Date time; //日期
    private String message; //消息
    private boolean readSign; //读取标志
    private int count; //未读条数

    @Ignore
    public MessageInfo() {
    }

    @Ignore
    public MessageInfo(Talk talk,boolean state) {
        this(talk.getId(),talk.getSender(),talk.getReceiver(),talk.getTime(),talk.getMessage(),state);
    }

    public MessageInfo(Long id, String sender, String receiver, Date time, String message, boolean readSign) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.message = message;
        this.readSign = readSign;
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

    public boolean isReadSign() {
        return readSign;
    }

    public void setReadSign(boolean readSign) {
        this.readSign = readSign;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "MessageInfo{" +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", time=" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time) +
                ", message='" + message + '\'' +
                ", readSign=" + readSign +
                ", count=" + count +
                '}';
    }

}
