package com.example.taobaounion.view;

import com.example.taobaounion.base.IBaseCallBack;
import com.example.taobaounion.model.bean.Histories;
import com.example.taobaounion.model.bean.SearchRecommend;
import com.example.taobaounion.model.bean.SearchResult;

import java.util.List;

public interface ISearchCallBack extends IBaseCallBack {
    void loadHistory(Histories histories); //搜索记录

    void delHistory(); //删除搜索记录

    void searchResult(SearchResult result);//搜索结果

    void MoreLoaded(SearchResult result);//加载更多

    void MoreLoadedError();//出错

    void MoreLoadedEmpty();//没数据

    void getRecommendationWords(List<SearchRecommend.DataBean> words);//推荐词
}
