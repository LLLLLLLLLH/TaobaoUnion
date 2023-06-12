package com.example.taobaounion.presenter;

import com.example.taobaounion.model.bean.RegisterInfo;

public interface IBaseUserPresenter {
    void checkSmsCode(RegisterInfo.Check info);
    void getPhoneVerifyCode(RegisterInfo.Send info);
}
