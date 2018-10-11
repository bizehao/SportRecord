package com.bzh.sportrecord.greenModel;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/8 14:50
 */
@Entity
public class FriendsInfo {
    @Id(autoincrement = true)
    private Long id; //id
    private String username; //用户名
    private String name; // 姓名
    private String headportrait; //头像
    private String descript; //描述
    private String address; //地址
    private String motto; //座右铭
    private String remarkname; //备注
    @Generated(hash = 2113635984)
    public FriendsInfo(Long id, String username, String name, String headportrait,
            String descript, String address, String motto, String remarkname) {
        this.id = id;
        this.username = username;
        this.name = name;
        this.headportrait = headportrait;
        this.descript = descript;
        this.address = address;
        this.motto = motto;
        this.remarkname = remarkname;
    }
    @Generated(hash = 348152607)
    public FriendsInfo() {
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
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getHeadportrait() {
        return this.headportrait;
    }
    public void setHeadportrait(String headportrait) {
        this.headportrait = headportrait;
    }
    public String getDescript() {
        return this.descript;
    }
    public void setDescript(String descript) {
        this.descript = descript;
    }
    public String getAddress() {
        return this.address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getMotto() {
        return this.motto;
    }
    public void setMotto(String motto) {
        this.motto = motto;
    }
    public String getRemarkname() {
        return this.remarkname;
    }
    public void setRemarkname(String remarkname) {
        this.remarkname = remarkname;
    }
}
