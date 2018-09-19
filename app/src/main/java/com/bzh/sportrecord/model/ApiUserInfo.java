package com.bzh.sportrecord.model;

/**
 * 用户信息
 */
public class ApiUserInfo {

    /**
     * code : 200
     * data : {"name":"李四","headportrait":"222","descript":"喜欢打羽毛球","address":"山西,运城","motto":"人无完人,天无完天"}
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
         * name : 李四
         * headportrait : 222
         * descript : 喜欢打羽毛球
         * address : 山西,运城
         * motto : 人无完人,天无完天
         */

        private String name;
        private String headportrait;
        private String descript;
        private String address;
        private String motto;

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

        @Override
        public String toString() {
            return "DataBean{" +
                    "name='" + name + '\'' +
                    ", headportrait='" + headportrait + '\'' +
                    ", descript='" + descript + '\'' +
                    ", address='" + address + '\'' +
                    ", motto='" + motto + '\'' +
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
