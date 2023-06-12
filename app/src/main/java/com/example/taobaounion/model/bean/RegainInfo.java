package com.example.taobaounion.model.bean;

import com.google.gson.annotations.SerializedName;

public class RegainInfo {

    public static class modify
    {
        @SerializedName("oldPwd")
        String oldPwd;
        @SerializedName("newPwd")
        String newPwd;
        @SerializedName("captcha")
        String captcha;

        public modify(String oldPwd, String newPwd, String captcha) {
            this.oldPwd = oldPwd;
            this.newPwd = newPwd;
            this.captcha = captcha;
        }
    }

    public static class forget
    {
        @SerializedName("phoneNum")
        String phoneNum;
        @SerializedName("password")
        String password;

        public forget(String phoneNum, String password) {
            this.phoneNum = phoneNum;
            this.password = password;
        }
    }
}
