package com.example.taobaounion.model.bean;

import java.io.Serializable;

public class UserData implements Serializable {
    private boolean isLogin;
    private String nickname;
    private String avatar;
    private String sign;
    private int followCount;
    private int momentCount;
    private int fansCount;
    private boolean vip;
    private String area;
    private String position;
    private String company;
    private int sob;

    private String phone;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isVip() {
        return vip;
    }

    public void setVip(boolean vip) {
        this.vip = vip;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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

    public String getSob() {
        return String.valueOf(sob);
    }

    public void setSob(int sob) {
        this.sob = sob;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
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

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    public String getFollowCount() {
        return String.valueOf(followCount);
    }

    public void setFollowCount(int followCount) {
        this.followCount = followCount;
    }

    public String getMomentCount() {
        return String.valueOf(momentCount);
    }

    public void setMomentCount(int momentCount) {
        this.momentCount = momentCount;
    }

    public String getFansCount() {
        return String.valueOf(fansCount);
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }
}
