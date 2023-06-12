package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.ISelectPageCallBack;

public interface ISelectPagePresenter extends IBasePresenter<ISelectPageCallBack> {

    //精选页面获取分类
    void getCategories();

    //获取分类内容
    void getContentCategories(int categoryId);

    //重新加载内容
    void reloadContent();
}
