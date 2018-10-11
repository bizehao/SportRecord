package com.bzh.sportrecord.model;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/8 11:23
 */
public class ApiaddFriends {
    /**
     * code : 200
     * data : false
     * RequestMessage : 添加失败，该好友已添加
     */

    private String code;
    private boolean data;
    private String RequestMessage;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isData() {
        return data;
    }

    public void setData(boolean data) {
        this.data = data;
    }

    public String getRequestMessage() {
        return RequestMessage;
    }

    public void setRequestMessage(String RequestMessage) {
        this.RequestMessage = RequestMessage;
    }
}
