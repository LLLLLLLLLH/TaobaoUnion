package com.example.taobaounion.base;

public interface IBasePresenter<T> {

    //注册UI通知接口
    void registerViewCallBack(T callBack);


    //注销UI通知接口
    void unregisterViewCallBack(T callBack);
}
