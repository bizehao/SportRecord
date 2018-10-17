package com.bzh.sportrecord.greenModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.util.Date;

/**
 * @author 毕泽浩
 * @Description: 消息信息表  (autoincrement = true)
 * @time 2018/10/14 18:49
 */
@Entity
public class MessageInfo {
    @Id
    private Long id; //id
    private String sender; //发送者
    private String receiver; //接受者
    private Date time; //日期
    private String message; //消息
    private boolean readSign; //读取标志
    @Generated(hash = 160769317)
    public MessageInfo(Long id, String sender, String receiver, Date time,
            String message, boolean readSign) {
        this.id = id;
        this.sender = sender;
        this.receiver = receiver;
        this.time = time;
        this.message = message;
        this.readSign = readSign;
    }
    @Generated(hash = 1292770546)
    public MessageInfo() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getReceiver() {
        return this.receiver;
    }
    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
    public Date getTime() {
        return this.time;
    }
    public void setTime(Date time) {
        this.time = time;
    }
    public String getMessage() {
        return this.message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean getReadSign() {
        return this.readSign;
    }
    public void setReadSign(boolean readSign) {
        this.readSign = readSign;
    }


}
