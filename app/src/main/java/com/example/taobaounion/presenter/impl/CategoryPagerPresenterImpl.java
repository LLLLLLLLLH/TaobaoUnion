package com.example.taobaounion.presenter.impl;

import androidx.annotation.NonNull;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.presenter.ICategoryPagerPresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.ICategoryPagerCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class CategoryPagerPresenterImpl implements ICategoryPagerPresenter {


    private Map<Integer, Integer> pages = new HashMap<>();

    public static final int DEFAULT_PAGE = 1;
    private static ICategoryPagerPresenter instance = null;
    private Integer mCurrentPage;

    public static ICategoryPagerPresenter getInstance() {
        if (instance == null)
            instance = new CategoryPagerPresenterImpl();
        return instance;
    }

    public CategoryPagerPresenterImpl() {
    }

    //根据分类id去加载内容
    @Override
    public void getContentByCategoryId(int categoryId) {

        for (ICategoryPagerCallBack callBack : mCallBacks) {
            if (callBack.getCategoryId() == categoryId)
                callBack.onLoading();
        }
        Integer page = pages.get(categoryId);
        if (page == null) {
            page = DEFAULT_PAGE;
            pages.put(categoryId, page);
        }

        Call<HomePagerContent> task = createTask(categoryId, page);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(@NonNull Call<HomePagerContent> call, @NonNull Response<HomePagerContent> response) {
                int code = response.code();
                LogUtils.d(this, "已执行");
                if (code == HttpsURLConnection.HTTP_OK) {
                    HomePagerContent pagerContent = response.body();
                    if (pagerContent != null) {
                        handleHomePageContentResult(pagerContent, categoryId);
                        LogUtils.d(this, pagerContent);
                    }
                } else {
                    handleNetWork(categoryId);
                }
            }

            @Override
            public void onFailure(@NonNull Call<HomePagerContent> call, @NonNull Throwable t) {
                LogUtils.d(this, "执行到这儿咯");
                handleNetWork(categoryId);
                LogUtils.d(this, t);
            }
        });
    }

    private Call<HomePagerContent> createTask(int categoryId, Integer page) {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, page);
        LogUtils.d(this, homePagerUrl);
        Call<HomePagerContent> task = api.GET_PAGER_CONTENT(homePagerUrl);
        return task;
    }

    private void handleNetWork(int categoryId) {
        for (ICategoryPagerCallBack callBack : mCallBacks) {
            if (callBack.getCategoryId() == categoryId)
                callBack.onError();
        }
    }


    private void handleHomePageContentResult(HomePagerContent homePagerContent, int categoryId) {
        List<HomePagerContent.DataBean> data = homePagerContent.getData();
        for (ICategoryPagerCallBack callBack : mCallBacks) {
            if (callBack.getCategoryId() == categoryId) {
                if (homePagerContent.getData().size() == 0) {
                    callBack.onEmpty();
                } else {
                    List<HomePagerContent.DataBean> looperData = data.subList(data.size() - 5, data.size());
                    callBack.onLooperListLoaded(looperData);
                    callBack.onContentLoaded(data);
                }
            }
        }
    }

    //加载更多
    //1.拿到当前页面
    //2.页码++
    //3.加载数据
    //4.处理数据结果
    @Override
    public void loadMore(int categoryId) {
        mCurrentPage = pages.get(categoryId);
        if (mCurrentPage == null) {
            mCurrentPage = 1;
        }
        mCurrentPage++;
        Call<HomePagerContent> task = createTask(categoryId, mCurrentPage);
        LogUtils.d(this,"idididiidi-------------------->"+categoryId);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                int code = response.code();
                if (code == HttpsURLConnection.HTTP_OK) {
                    HomePagerContent body = response.body();
                    LogUtils.d(this,body);
                    handelLoadMoreResult(body,categoryId);
                } else {
                    handleLoadMoreError(categoryId);
                }

            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                LogUtils.e(this, t);
                handleLoadMoreError(categoryId);
            }
        });
    }

    private void handelLoadMoreResult(HomePagerContent body, int categoryId) {
        for (ICategoryPagerCallBack callBack : mCallBacks) {
            if (callBack.getCategoryId() == categoryId) {
             if (body == null || body.getData().size() == 0)
             {
                 callBack.onLoadMoreEmpty();
             }
             else
             {
                 callBack.onLoadMoreLoaded(body.getData());
             }
            }
        }
    }

    private void handleLoadMoreError(int categoryId) {
        mCurrentPage--;
        pages.put(categoryId,mCurrentPage);
        for (ICategoryPagerCallBack callBack : mCallBacks) {
            if (callBack.getCategoryId() == categoryId)
                callBack.onLoadMoreError();
        }
    }

    @Override
    public void reload(int categoryId) {

    }

    @Override
    public void registerViewCallBack(ICategoryPagerCallBack callBack) {
        if (!mCallBacks.contains(callBack)) {
            mCallBacks.add(callBack);
        }

    }

    @Override
    public void unregisterViewCallBack(ICategoryPagerCallBack callBack) {
        mCallBacks.remove(callBack);
    }


    private List<ICategoryPagerCallBack> mCallBacks = new ArrayList<>();


}
