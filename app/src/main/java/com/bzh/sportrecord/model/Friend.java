package com.bzh.sportrecord.model;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * @author 毕泽浩
 * @Description: 朋友 pojo
 * @time 2018/9/30 10:49
 */
public class Friend implements Serializable, Comparable<Friend>{

    private String name;
    private String remarks; //备注
    private String image;
    private String pinyin; //拼音

    public Friend(String name) {
        this.name = name;
    }

    public Friend(String name, String remarks, String image, String pinyin) {
        this.name = name;
        this.remarks = remarks;
        this.image = image;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", remarks='" + remarks + '\'' +
                ", pinyin='" + pinyin + '\'' +
                '}';
    }

    @Override
    public int compareTo(@NonNull Friend o) {
        return pinyin.compareTo(o.pinyin);
    }
}
