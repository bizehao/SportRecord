package com.bzh.sportrecord.model;

public class ApiRegister {

    /**
     * code : 422
     * data : 0
     * message : 注册失败
     */

    private String code;
    private int data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
