package com.example.taobaounion.model.bean;

import java.io.Serializable;

public class UserInfo implements Serializable {

    /**
     * success : true
     * code : 10000
     * message : 获取用户信息成功.
     * data : {"userId":"1153952789488054272","nickname":"拉大锯","avatar":"https://imgs.sunofbeaches.com/group1/M00/00/07/rBsADV22ZymAV8BwAABVL9XtNSU926.png","position":"首席打杂工程师","company":"阳光沙滩","sign":"三界之内没有我解不了的bug","area":"广东省/深圳市/福田区","vip":false}
     */

    private boolean success;
    private int code;
    private String message;
    private DataBean data;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * userId : 1153952789488054272
         * nickname : 拉大锯
         * avatar : https://imgs.sunofbeaches.com/group1/M00/00/07/rBsADV22ZymAV8BwAABVL9XtNSU926.png
         * position : 首席打杂工程师
         * company : 阳光沙滩
         * sign : 三界之内没有我解不了的bug
         * area : 广东省/深圳市/福田区
         * vip : false
         */

        private boolean isLogin;
        private String userId;
        private String nickname;
        private String avatar;
        private String position;
        private String company;
        private String sign;
        private String area;
        private boolean vip;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public boolean isVip() {
            return vip;
        }

        public void setVip(boolean vip) {
            this.vip = vip;
        }

        public boolean isLogin() {
            return isLogin;
        }

        public void setLogin(boolean login) {
            isLogin = login;
        }
    }
}
