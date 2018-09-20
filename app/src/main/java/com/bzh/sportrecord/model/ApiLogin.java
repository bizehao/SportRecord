package com.bzh.sportrecord.model;

/**
 * 登陆的实体类
 */
public class ApiLogin {


    /**
     * code : 200
     * data : {"X_Auth_Token":"eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFuZ3NhbiIsImNyZWF0ZWQiOjE1MzcyMzMzNzA0ODUsImV4cCI6MTUzNzgzODE3MH0.wgxZqnfAVpqHM8i7jKJef2MqC2KZAWnx0aAJchwGEGmTbo5HEFsaLF0_G-XHe6zNp3VIOqc5M28fW-B7YLcKnQ"}
     * message :
     */

    private String code;
    private DataBean data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class DataBean {
        /**
         * X_Auth_Token : eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ6aGFuZ3NhbiIsImNyZWF0ZWQiOjE1MzcyMzMzNzA0ODUsImV4cCI6MTUzNzgzODE3MH0.wgxZqnfAVpqHM8i7jKJef2MqC2KZAWnx0aAJchwGEGmTbo5HEFsaLF0_G-XHe6zNp3VIOqc5M28fW-B7YLcKnQ
         */

        private String X_Auth_Token;

        private String username;

        public String getX_Auth_Token() {
            return X_Auth_Token;
        }

        public void setX_Auth_Token(String X_Auth_Token) {
            this.X_Auth_Token = X_Auth_Token;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "X_Auth_Token='" + X_Auth_Token + '\'' +
                    ", username='" + username + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApiLogin{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
