package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.IDiscountsCallBack;

public interface IDiscountsPresenter extends IBasePresenter<IDiscountsCallBack> {
    void getDiscountContent();
    void reLoad();
    void loadMore();
}
