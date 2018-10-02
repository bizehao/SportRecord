package com.bzh.sportrecord.model;

/**
 * @author 毕泽浩
 * @Description: 朋友 pojo
 * @time 2018/9/30 10:49
 */
public class Friend {

    private String name;
    private int imageId;
    private String remarks; //备注
    private String pinyin; //拼音

    public Friend(String name, String pinyin) {
        this.name = name;
        this.pinyin = pinyin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
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
}
