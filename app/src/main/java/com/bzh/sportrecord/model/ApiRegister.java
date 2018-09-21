package com.bzh.sportrecord.model;

public class ApiRegister {

    /**
     * code : 200
     * data : null
     * message : 注册成功
     */

    private String code;
    private Object data;
    private String message;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "ApiRegister{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
