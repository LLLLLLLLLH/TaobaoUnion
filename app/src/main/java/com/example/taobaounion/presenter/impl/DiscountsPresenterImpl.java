package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.DiscountsContent;
import com.example.taobaounion.presenter.IDiscountsPresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.IDiscountsCallBack;

import javax.net.ssl.HttpsURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DiscountsPresenterImpl implements IDiscountsPresenter {

    private IDiscountsCallBack mCallBack;

    public static final int DEFAULT_PAGE = 1;

    public int mCurrentPage = DEFAULT_PAGE;
    private API mApi;


    public DiscountsPresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        mApi = retrofit.create(API.class);
    }

    @Override
    public void registerViewCallBack(IDiscountsCallBack callBack) {
        if (mCallBack == null)
            mCallBack = callBack;
    }

    @Override
    public void unregisterViewCallBack(IDiscountsCallBack callBack) {
        if (mCallBack != null) {
            mCallBack = null;
        }
    }

    @Override
    public void getDiscountContent() {
        if (mCallBack != null) {
            mCallBack.onLoading();
        }
        mCurrentPage = 1;
        LogUtils.d(this, "getDiscountsContent------->" + "这里");
        String discountsUrl = UrlUtils.getDiscountsUrl(mCurrentPage);
        LogUtils.d(this, "getDiscountsContent------->" + discountsUrl);
        Call<DiscountsContent> task = mApi.GET_DISCOUNTS_CONTENT(discountsUrl);
        task.enqueue(new Callback<DiscountsContent>() {
            @Override
            public void onResponse(Call<DiscountsContent> call, Response<DiscountsContent> response) {
                LogUtils.d(this, "getDiscountsContent------->" + response.code());
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    DiscountsContent body = response.body();
                    onSuccess(body);
                } else {
                    mCallBack.onError();
                }
            }

            @Override
            public void onFailure(Call<DiscountsContent> call, Throwable t) {
                LogUtils.d(this, "getDiscountsContent------->" + t);
                mCallBack.onError();
            }
        });
    }

    private void onError() {
        if (mCallBack != null)
            mCallBack.onLoadMoreError();
    }

    private void onSuccess(DiscountsContent body) {
        if (mCallBack != null) {
            mCallBack.successfullyLoaded(body);
        }
    }

    @Override
    public void reLoad() {
        this.getDiscountContent();
    }

    @Override
    public void loadMore() {
        mCurrentPage++;
        String discountsUrl = UrlUtils.getDiscountsUrl(mCurrentPage);
        Call<DiscountsContent> task = mApi.GET_DISCOUNTS_CONTENT(discountsUrl);
        task.enqueue(new Callback<DiscountsContent>() {
            @Override
            public void onResponse(Call<DiscountsContent> call, Response<DiscountsContent> response) {
                if (response.code() == HttpsURLConnection.HTTP_OK) {
                    DiscountsContent body = response.body();
                    if (body.getData().getTbk_dg_optimus_material_response().getResult_list().getMap_data().size() != 0) {
                        mCallBack.onLoadMore(body);
                    } else {
                        mCallBack.onLoadMoreEmpty();
                        mCurrentPage--;
                    }
                } else {
                    mCallBack.onLoadMoreError();
                    mCurrentPage--;
                }
            }

            @Override
            public void onFailure(Call<DiscountsContent> call, Throwable t) {
                mCallBack.onLoadMoreError();
                mCurrentPage--;
            }
        });
    }

}
