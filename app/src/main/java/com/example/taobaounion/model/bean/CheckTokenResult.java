package com.example.taobaounion.model.bean;

public class CheckTokenResult {


    /**
     * success : true
     * code : 10000
     * message : 操作成功
     * data : {"id":"1628303351709097986","roles":null,"lev":0,"isVip":"0","avatar":"https://images.sunofbeaches.com//images/2023_06_07/1666474366381252609.png","nickname":"脑壳痛","token":null,"fansCount":null,"followCount":null}
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

    public static class DataBean {
        /**
         * id : 1628303351709097986
         * roles : null
         * lev : 0
         * isVip : 0
         * avatar : https://images.sunofbeaches.com//images/2023_06_07/1666474366381252609.png
         * nickname : 脑壳痛
         * token : null
         * fansCount : null
         * followCount : null
         */

        private String id;
        private Object roles;
        private int lev;
        private String isVip;
        private String avatar;
        private String nickname;
        private Object token;
        private Object fansCount;
        private Object followCount;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Object getRoles() {
            return roles;
        }

        public void setRoles(Object roles) {
            this.roles = roles;
        }

        public int getLev() {
            return lev;
        }

        public void setLev(int lev) {
            this.lev = lev;
        }

        public String getIsVip() {
            return isVip;
        }

        public void setIsVip(String isVip) {
            this.isVip = isVip;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public Object getToken() {
            return token;
        }

        public void setToken(Object token) {
            this.token = token;
        }

        public Object getFansCount() {
            return fansCount;
        }

        public void setFansCount(Object fansCount) {
            this.fansCount = fansCount;
        }

        public Object getFollowCount() {
            return followCount;
        }

        public void setFollowCount(Object followCount) {
            this.followCount = followCount;
        }
    }
}
