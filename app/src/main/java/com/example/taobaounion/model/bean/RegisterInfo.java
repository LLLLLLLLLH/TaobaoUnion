package com.example.taobaounion.model.bean;
import com.google.gson.annotations.SerializedName;

public class RegisterInfo {


    public static class Send {
        @SerializedName("phoneNumber")
        public String phoneNumber;
        @SerializedName("verifyCode")
        public String verifyCode;

        public Send(String phoneNumber, String verifyCode) {
            this.phoneNumber = phoneNumber;
            this.verifyCode = verifyCode;
        }
    }

    public static class Check {
        public String phone;
        public String smsCode;

        public String getPhone() {
            return phone;
        }

        public String getSmsCode() {
            return smsCode;
        }

        public Check(String phone, String smsCode) {
            this.phone = phone;
            this.smsCode = smsCode;
        }
    }

    public static class Register {
        @SerializedName("phoneNumber")
        public String phone;
        @SerializedName("password")
        public String password;
        @SerializedName("nickname")
        public String nickName;

        public Register(String phone, String password, String nickName) {
            this.phone = phone;
            this.password = password;
            this.nickName = nickName;
        }
    }

}