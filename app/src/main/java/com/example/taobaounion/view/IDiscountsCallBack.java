package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallBack;
import com.example.taobaounion.model.bean.DiscountsContent;

public interface IDiscountsCallBack extends IBaseCallBack {

    void successfullyLoaded(DiscountsContent data);

    void onLoadMore(DiscountsContent data);

    void onLoadMoreError();

    void onLoadMoreEmpty();

}
