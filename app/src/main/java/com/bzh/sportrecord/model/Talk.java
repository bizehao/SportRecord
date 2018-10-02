package com.bzh.sportrecord.model;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/9/28 14:06
 */
public class Talk {
    private String code;
    private String sender;
    private String receiver;
    private Data data = new Data();

    public Talk() {
        setCode("200");
        setSender("张三");
    }

    static class Data{
        private String time;
        private String message;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    //设置消息体
    public void setMessage(String message) {
        this.data.setMessage(message);
    }

}
