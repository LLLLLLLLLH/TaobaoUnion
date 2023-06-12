package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.IHomeCallBack;

public interface IHomePresenter extends IBasePresenter<IHomeCallBack> {
    //获取商品分类
    void getCategories();
}


