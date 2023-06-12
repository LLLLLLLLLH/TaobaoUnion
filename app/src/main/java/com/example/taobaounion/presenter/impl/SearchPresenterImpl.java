package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.Histories;
import com.example.taobaounion.model.bean.SearchRecommend;
import com.example.taobaounion.model.bean.SearchResult;
import com.example.taobaounion.presenter.ISearchPresenter;
import com.example.taobaounion.utils.JsonCacheUtil;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.view.ISearchCallBack;

import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchPresenterImpl implements ISearchPresenter {

    private static final int DEFAULT_PAGE = 1;
    private static final String KEY_HISTORY = "KEY_HISTORY";
    private static final int HISTORIES_MAX_SIZE = 10;

    private int mCurrentPage = DEFAULT_PAGE;

    private final API mApi;
    private ISearchCallBack mCallBack;
    private String mCurrentKeyword;
    private JsonCacheUtil mCacheUtil;

    public SearchPresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(API.class);
        mCacheUtil = JsonCacheUtil.getInstance();
    }

    @Override
    public void registerViewCallBack(ISearchCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void unregisterViewCallBack(ISearchCallBack callBack) {
        mCallBack = null;
    }

    @Override
    public void getSearchHistory() {
        Histories cache = mCacheUtil.getValue(KEY_HISTORY, Histories.class);
        if (mCallBack != null)
        {
            mCallBack.loadHistory(cache);
        }
    }

    @Override
    public void deleteSearchHistory() {
        mCacheUtil.delCache(KEY_HISTORY);
        if (mCallBack != null) {
            mCallBack.delHistory();
        }
    }

    //添加历史记录
    private void addHistory(String history)
    {
        Histories histories = mCacheUtil.getValue(KEY_HISTORY,Histories.class);
        List<String> historiesList = null;
        if (histories != null && histories.getHistories()!=null) {
            historiesList = histories.getHistories();
            if (historiesList.contains(history))
                mCacheUtil.delCache(history);
        }

        if (historiesList == null) {
            historiesList = new ArrayList<>();
        }

        if (histories == null) {
            histories = new Histories();
        }
        histories.setHistories(historiesList);

        //数据限制
        if (historiesList.size() > HISTORIES_MAX_SIZE)
        {
            historiesList.subList(0,HISTORIES_MAX_SIZE);
        }

        historiesList.add(history); //添加保存记录
        mCacheUtil.saveCache(KEY_HISTORY,histories);
    }

    @Override
    public void doSearch(String keyWord) {
        if (mCurrentKeyword == null || !mCurrentKeyword.equals(keyWord)) {
            mCurrentKeyword = keyWord;
            addHistory(keyWord);
        }
        if (mCallBack != null) {
            mCallBack.onLoading();
        }
        LogUtils.d(this,"page-->"+mCurrentPage+"  word---->"+keyWord);
        Call<SearchResult> task = mApi.GET_SEARCH_RESULT(mCurrentPage, keyWord);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                ToastUtils.showToast("已执行");
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    LogUtils.d(this,"code-------------------->"+response.code());
                    handleSearchResult(response.body());
                } else {
                    onError();
                }
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                onError();
            }
        });
    }

    private void onError() {
        if (mCallBack != null) {
            mCallBack.onError();
        }
    }

    private void handleSearchResult(SearchResult body) {
        if (isResultEmpty(body)) {
            mCallBack.onEmpty();
        } else {
            mCallBack.searchResult(body);
        }
    }

    private boolean isResultEmpty(SearchResult body) {
        try {
            return body == null || body.getData().getTbk_dg_material_optional_response().getResult_list().getMap_data().size() == 0;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public void reSearch() {
        if (mCurrentKeyword != null) {
            this.doSearch(mCurrentKeyword);
        } else {
            if (mCallBack != null)
                mCallBack.onEmpty();
        }
    }

    @Override
    public void LoadMore() {
        mCurrentPage++;
        if (mCurrentKeyword == null) {
            if (mCallBack != null) {
                mCallBack.onEmpty();
            }
        } else {
            LoadMoreSearch();
        }
    }

    private void LoadMoreSearch() {
        Call<SearchResult> task = mApi.GET_SEARCH_RESULT(mCurrentPage, mCurrentKeyword);
        task.enqueue(new Callback<SearchResult>() {
            @Override
            public void onResponse(Call<SearchResult> call, Response<SearchResult> response) {
                if (response.code() == HttpURLConnection.HTTP_OK)
                    handlerLoadMoreSearch(response.body());
                else
                    onLoadedMoreError();
            }

            @Override
            public void onFailure(Call<SearchResult> call, Throwable t) {
                t.printStackTrace();
                onLoadedMoreError();
            }
        });
    }

    private void handlerLoadMoreSearch(SearchResult body) {
        if (mCallBack != null) {
            if (isResultEmpty(body)) {
                mCallBack.MoreLoadedEmpty(); //空数据状态
            } else {
                mCallBack.MoreLoaded(body); //加载更多
            }
        }
    }

    private void onLoadedMoreError() {
        if (mCallBack != null) {
            mCallBack.MoreLoadedError();
        }
    }

    @Override
    public void getRecommendation() {
        Call<SearchRecommend> task = mApi.GET_SEARCH_RECOMMEND();
        task.enqueue(new Callback<SearchRecommend>() {
            @Override
            public void onResponse(Call<SearchRecommend> call, Response<SearchRecommend> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    SearchRecommend body = response.body();
                    if (mCallBack != null) {
                        mCallBack.getRecommendationWords(body.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchRecommend> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}


