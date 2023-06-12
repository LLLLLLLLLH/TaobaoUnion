package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.model.bean.HomePagerContent;
import com.example.taobaounion.presenter.ISelectPagePresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.ISelectPageCallBack;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SelectPagePresenterImpl implements ISelectPagePresenter {

    private static final Integer DEFAULT_PAGE = 1;
    private final API mApi;

    private Map<Integer, Integer> pages = new HashMap<>();
    private int mCurrentCategoryId;
    //private SelectPageCategory.DataBean mCurrCategories;

    public SelectPagePresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(API.class);
    }

    private ISelectPageCallBack mViewCallBack = null;

    @Override
    public void getCategories() {

        //Call<SelectPageCategory> task = mApi.GET_SELECT_PAGE_CATEGORY();
/*            @Override
            public void onResponse(Call<SelectPageCategory> call, Response<SelectPageCategory> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    SelectPageCategory body = response.body();
                    if (mViewCallBack != null) {
                        mViewCallBack.onCategoriesLoaded(body);
                    } else {
                        onLoadedError();
                    }
                }
            }

            @Override
            public void onFailure(Call<SelectPageCategory> call, Throwable t) {
                onLoadedError();
            }
        });*/
        Call<Categories> task = mApi.GET_SELECT_PAGE_CATEGORY();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    Categories body = response.body();
                    if (mViewCallBack != null) {
                        mViewCallBack.onCategoriesLoaded(body);
                    } else
                        onLoadedError();
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                onLoadedError();
            }
        });
    }

    private void onLoadedError() {
        if (mViewCallBack != null) {
            mViewCallBack.onError();
        }
    }

    @Override
    public void getContentCategories(int categoryId) {
     /*   mCurrCategories = bean;
        if (mCurrCategories != null) {
            Call<SelectedContent> task = mApi.GET_SELECT_CONTENT(bean.getFavorites_id());
            task.enqueue(new Callback<SelectedContent>() {
                @Override
                public void onResponse(Call<SelectedContent> call, Response<SelectedContent> response) {
                    if (response.code() == HttpURLConnection.HTTP_OK) {
                        SelectedContent body = response.body();
                        if (mViewCallBack != null) {
                            mViewCallBack.onContentLoaded(body);
                        }
                    } else {
                        onLoadedError();
                    }
                }

                @Override
                public void onFailure(Call<SelectedContent> call, Throwable t) {
                    onLoadedError();
                }
            });
        }*/
/*
        if (mViewCallBack.getCategoryId() == categoryId) {
           mViewCallBack.onLoading();
        }*/

        if (mViewCallBack != null) {
            mViewCallBack.onLoading();
        }

        LogUtils.d(this,"categoryId------------>"+categoryId);

        mCurrentCategoryId = categoryId;

        Integer page = pages.get(categoryId);
        if (page == null) {
            page = DEFAULT_PAGE;
            pages.put(categoryId, page);
        }
        LogUtils.d(this,"Id ---->"+categoryId+"    page------->"+page);
        Call<HomePagerContent> task = createTask(categoryId,page);
        task.enqueue(new Callback<HomePagerContent>() {
            @Override
            public void onResponse(Call<HomePagerContent> call, Response<HomePagerContent> response) {
                LogUtils.d(this,"-------------------->这里"+response.code());
                if (response.code() == HttpURLConnection.HTTP_OK)
                {

                    HomePagerContent body = response.body();
                    if (body != null) {
                        LogUtils.d(this,"-------------------->"+body.getData().size());
                        handleHomePageContentResult(body, categoryId);
                    }
                }
            }

            @Override
            public void onFailure(Call<HomePagerContent> call, Throwable t) {
                handleNetwork(categoryId);
                LogUtils.d(this,"-------------------->这里");
            }
        });

    }

    private void handleNetwork(int categoryId) {
        if (mViewCallBack.getCategoryId() == categoryId) {
            mViewCallBack.onError();
        }
    }

    private void handleHomePageContentResult(HomePagerContent body, int categoryId) {
        List<HomePagerContent.DataBean> data = body.getData();
            if (mViewCallBack.getCategoryId() == categoryId) {
                if (body.getData().size() == 0) {
                    mViewCallBack.onEmpty();
                } else {
                    mViewCallBack.onContentLoaded(data);
                }
            }
    }

    private Call<HomePagerContent> createTask(int categoryId, Integer page) {
        String homePagerUrl = UrlUtils.createHomePagerUrl(categoryId, page);
        LogUtils.d(this,"homePageUrl--------------->"+homePagerUrl);
        Call<HomePagerContent> task = mApi.GET_SELECT_CONTENT(homePagerUrl);
        return task;
    }

    @Override
    public void reloadContent() {
        this.getCategories();
    }

    @Override
    public void registerViewCallBack(ISelectPageCallBack callBack) {
        mViewCallBack = callBack;
    }

    @Override
    public void unregisterViewCallBack(ISelectPageCallBack callBack) {
        if (mViewCallBack != null) {
            mViewCallBack = null;
        }
    }

}
