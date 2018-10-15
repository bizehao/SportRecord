package com.bzh.sportrecord.greenModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author 毕泽浩
 * @Description: 消息信息表
 * @time 2018/10/14 18:49
 */
@Entity
public class MessageInfo {
    @Id(autoincrement = true)
    private Long id; //id
    private String username; //用户名
    private String sender; //发送者
    private String dateTime; //日期
    private String message; //消息
    private boolean readSign; //读取标志
    @Generated(hash = 1741365646)
    public MessageInfo(Long id, String username, String sender, String dateTime,
            String message, boolean readSign) {
        this.id = id;
        this.username = username;
        this.sender = sender;
        this.dateTime = dateTime;
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
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getSender() {
        return this.sender;
    }
    public void setSender(String sender) {
        this.sender = sender;
    }
    public String getDateTime() {
        return this.dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
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
