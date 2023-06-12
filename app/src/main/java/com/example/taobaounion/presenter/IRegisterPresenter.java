package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.model.bean.RegisterInfo;
import com.example.taobaounion.view.IRegisterCallBack;

public interface IRegisterPresenter extends IBasePresenter<IRegisterCallBack>, IBaseUserPresenter {
    void register(String smsCode,RegisterInfo.Register info);
}
