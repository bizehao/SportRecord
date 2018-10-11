package com.bzh.sportrecord.model;

import java.util.List;

/**
 * 用户信息(模糊搜索，多个用户)
 */
public class ApiUserInfos {

    /**
     * code : 200
     * data : {"username":"***","name":"李四","headportrait":"222","descript":"喜欢打羽毛球","address":"山西,运城","motto":"人无完人,天无完天"}
     * message :
     */

    private String code;
    private List<DataBean> data;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
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
         * username: ***
         * name : 李四
         * headportrait : 222
         * descript : 喜欢打羽毛球
         * address : 山西,运城
         * motto : 人无完人,天无完天
         */
        private String username;
        private String name;
        private String headportrait;
        private String descript;
        private String address;
        private String motto;
        private boolean isExit;

        public DataBean(String username) {
            this.username = username;
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

        public boolean isExit() {
            return isExit;
        }

        public void setExit(boolean exit) {
            isExit = exit;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "username='" + username + '\'' +
                    ", name='" + name + '\'' +
                    ", headportrait='" + headportrait + '\'' +
                    ", descript='" + descript + '\'' +
                    ", address='" + address + '\'' +
                    ", motto='" + motto + '\'' +
                    ", isExit=" + isExit +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ApiUserInfo{" +
                "code='" + code + '\'' +
                ", data=" + data +
                ", message='" + message + '\'' +
                '}';
    }
}
