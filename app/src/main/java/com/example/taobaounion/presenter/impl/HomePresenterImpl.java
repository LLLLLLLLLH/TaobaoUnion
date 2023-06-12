package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.Categories;
import com.example.taobaounion.presenter.IHomePresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.view.IHomeCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class HomePresenterImpl implements IHomePresenter {

    private IHomeCallBack mCallBack = null;

    //获取数据
    @Override
    public void getCategories() {

        if (mCallBack != null) {
            mCallBack.onLoading();
        }
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        Call<Categories> task = api.GET_CATEGORIES();
        task.enqueue(new Callback<Categories>() {
            @Override
            public void onResponse(Call<Categories> call, Response<Categories> response) {
                int code = response.code();
                LogUtils.d(this, code);
                if (code == HttpURLConnection.HTTP_OK) {
                    Categories categories = response.body();
                    //请求成功
                    if (mCallBack != null) {
                        if (categories == null || categories.getData().size() == 0) {
                            mCallBack.onEmpty();
                        } else {
                            LogUtils.d(this, "请求成功");
                            mCallBack.onCategoriesLoad(categories);
                        }
                    } else {
                        if (mCallBack != null) {
                            mCallBack.onError();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<Categories> call, Throwable t) {
                if (mCallBack != null) {
                    mCallBack.onError();
                }
            }
        });
    }

    @Override
    public void registerViewCallBack(IHomeCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void unregisterViewCallBack(IHomeCallBack callBack) {
        mCallBack = null;
    }
}
