package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallBack;
import com.example.taobaounion.model.bean.HomePagerContent;

import java.util.List;

public interface ICategoryPagerCallBack extends IBaseCallBack {

    //数据加载回来
    void onContentLoaded(List<HomePagerContent.DataBean> contents);

    int getCategoryId();

    //加载更多时发生错误
    void onLoadMoreError();

    //没有更多
    void onLoadMoreEmpty();

    //加到了更多内容
    void onLoadMoreLoaded(List<HomePagerContent.DataBean> contents);

    //轮播图
    void onLooperListLoaded(List<HomePagerContent.DataBean> contents);
}
