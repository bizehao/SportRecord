package com.bzh.sportrecord.model;

import java.util.List;

/**
 * @author 毕泽浩
 * @Description:
 * @time 2018/10/8 13:22
 */
public class ApiFriends {

    /**
     * code : 200
     * data : [{"username":"test","name":null,"headportrait":null,"descript":null,"address":null,"motto":null,"remark_NAME":null},{"username":"zhangsan","name":"张三","headportrait":"111","descript":"男生,喜欢运动","address":"北京,丰台区","motto":"好好学习,天天向上","remark_NAME":null},{"username":"236554","name":null,"headportrait":null,"descript":null,"address":null,"motto":null,"remark_NAME":null}]
     * RequestMessage : 查询成功
     */

    private String code;
    private String RequestMessage;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRequestMessage() {
        return RequestMessage;
    }

    public void setRequestMessage(String RequestMessage) {
        this.RequestMessage = RequestMessage;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : test
         * name : null
         * headportrait : null
         * descript : null
         * address : null
         * motto : null
         * remark_NAME : null
         */

        private String username;
        private String name;
        private String headportrait;
        private String descript;
        private String address;
        private String motto;
        private String remarkName;

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

        public String getRemarkName() {
            return remarkName;
        }

        public void setRemarkName(String remarkName) {
            this.remarkName = remarkName;
        }
    }
}
