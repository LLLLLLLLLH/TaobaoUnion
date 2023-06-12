package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.model.bean.RegainInfo;
import com.example.taobaounion.view.IRegainCallBack;

public interface IRegainPresenter extends IBasePresenter<IRegainCallBack>, IBaseUserPresenter {
    void modify(RegainInfo.modify info);

    void getPasswordBySms(String smsCode,RegainInfo.forget info);
}
