package com.example.taobaounion.model.bean;

public class LoginInfo {
    private String phoneNum;
    private String password;

    public LoginInfo(String phone, String password) {
        this.phoneNum = phone;
        this.password = password;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
