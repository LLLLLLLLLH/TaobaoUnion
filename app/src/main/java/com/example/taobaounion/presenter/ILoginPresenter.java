package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.ILoginCallBack;

public interface ILoginPresenter extends IBasePresenter<ILoginCallBack> {

   // void getCode();

    void doLogin(String phone, String password, String verifyCode);

    void setUserAvatar(String phone);

    void checkToken();

    void getUserInfo();

    void doLogout();

    void getAchievement();



}
