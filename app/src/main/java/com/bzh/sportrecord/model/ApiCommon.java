package com.bzh.sportrecord.model;

/**
 * @author 毕泽浩
 * @Description: 通用
 * @time 2018/10/15 21:48
 */
public class ApiCommon {
    private String code;
    private Object data;
    private Object message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }
}
