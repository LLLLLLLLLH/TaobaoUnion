package com.example.taobaounion.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.taobaounion.base.BaseApplication;
import com.example.taobaounion.model.bean.UserAchievement;
import com.example.taobaounion.model.bean.UserData;
import com.example.taobaounion.model.bean.UserInfo;

public class UserInfoUtils {
    public static void savaInfo(UserInfo.DataBean data) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (data != null) {
            edit.putString("avatar", data.getAvatar());
            edit.putString("sign", data.getSign());
            edit.putString("nickname", data.getNickname());
            edit.putBoolean("isLogin", true);
            edit.putBoolean("vip",data.isVip());
            edit.putString("area",data.getArea());
            edit.putString("position",data.getPosition());
            edit.putString("company",data.getCompany());
        }else
            edit.putBoolean("isLogin",false);
        edit.apply();
    }

    public static UserData getInfo() {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserData data = new UserData();
        data.setAvatar(sp.getString("avatar", ""));
        data.setSign(sp.getString("sign", "1秒登录,体验更多功能"));
        data.setNickname(sp.getString("nickname", "点击登录"));
        data.setLogin(sp.getBoolean("isLogin",false));
        data.setFollowCount(sp.getInt("follow",0));
        data.setFansCount(sp.getInt("fans",0));
        data.setMomentCount(sp.getInt("moment",0));
        data.setVip(sp.getBoolean("vip",data.isVip()));
        data.setArea(sp.getString("area",""));
        data.setPosition(sp.getString("position",""));
        data.setCompany(sp.getString("company",""));
        data.setSob(sp.getInt("sob",0));
        data.setPhone(sp.getString("phone",""));
        return data;
    }


    public static void saveAchievement(UserAchievement.DataBean data) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        if (data != null) {
            edit.putInt("fans", data.getFansCount());
            edit.putInt("follow", data.getFollowCount());
            edit.putInt("moment", data.getMomentCount());
            edit.putInt("sob", data.getSob());
        }
        edit.apply();
    }

    public static void savePhone(String phone) {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("phone",phone);
        edit.apply();
    }

    public static void clearCache()
    {
        SharedPreferences sharedPreferences = BaseApplication.getAppContext().getSharedPreferences("userInfo",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


}
