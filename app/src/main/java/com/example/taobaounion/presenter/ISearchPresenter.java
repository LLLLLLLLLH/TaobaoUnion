package com.example.taobaounion.presenter;

import com.example.taobaounion.base.IBasePresenter;
import com.example.taobaounion.view.ISearchCallBack;

public interface ISearchPresenter extends IBasePresenter<ISearchCallBack> {
    void getSearchHistory();//历史搜索

    void deleteSearchHistory();//删除历史

    void doSearch(String keyWord);//搜索

    void reSearch();//重新搜索

    void LoadMore();//加载更多

    void getRecommendation();//获取推荐
}
