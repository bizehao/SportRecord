package com.bzh.sportrecord.data.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author 毕泽浩
 * @Description: 好友信息表
 * @time 2018/10/8 14:50
 */
@Entity
public class FriendsInfo {
    @PrimaryKey
    private Long id; //id
    private String username; //用户名
    private String name; // 姓名
    private String headportrait; //头像
    private String descript; //描述
    private String address; //地址
    private String motto; //座右铭
    private String remarkname; //备注

    @Ignore
    public FriendsInfo() {
    }

    public FriendsInfo(Long id, String username, String name, String headportrait, String descript,
                       String address, String motto, String remarkname) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.headportrait = headportrait;
        this.descript = descript;
        this.address = address;
        this.motto = motto;
        this.remarkname = remarkname;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadportrait() {
        return headportrait;
    }

    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }

    public String getDescript() {
        return descript;
    }

    public void setDescript(String descript) {
        this.descript = descript;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMotto() {
        return motto;
    }

    public void setMotto(String motto) {
        this.motto = motto;
    }

    public String getRemarkname() {
        return remarkname;
    }

    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }
}
