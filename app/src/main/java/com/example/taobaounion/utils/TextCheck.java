package com.example.taobaounion.utils;

import android.text.TextUtils;

public class TextCheck {


    public static boolean isRight(String phone, String password, String verifyCode) {
        return isPasswordValid(password) && isPhoneValid(phone) && isVerifyCodeValid(verifyCode);
    }


    public static boolean isPhoneValid(String phone) {
        if (TextUtils.isEmpty(phone) || phone.length() != 11) {
            return false;
        }
        return true;
    }

    public static boolean isPasswordValid(String password) {
        if (TextUtils.isEmpty(password) || password.length() < 6 || password.length() > 20) {
            return false;
        }
        return true;
    }


    public static boolean isVerifyCodeValid(String verifyCode) {
        if (TextUtils.isEmpty(verifyCode) || verifyCode.length() != 5) {
            return false;
        }
        return true;
    }

}
