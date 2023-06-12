package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.TicketParams;
import com.example.taobaounion.model.bean.TicketResult;
import com.example.taobaounion.presenter.ITicketPresenter;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.UrlUtils;
import com.example.taobaounion.view.ITicketCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class TicketPresenterImpl implements ITicketPresenter {

    private ITicketCallBack mTicketCallBack;
    private String mCover;
    private TicketResult mTicketResult;

    private enum STATE {
        LOADING,ERROR,SUCCESS,NONE
    }

    private STATE state = STATE.NONE;

    @Override
    public void registerViewCallBack(ITicketCallBack callBack) {
        if (state != STATE.NONE)
        {
            if (state == STATE.SUCCESS)
            {
                onLoadedSuccess();
            }else if (state == STATE.ERROR)
            {
                onLoadedError();
            }else if (state == STATE.LOADING)
                onTicketLoading();
        }
        mTicketCallBack = callBack;
    }

    @Override
    public void unregisterViewCallBack(ITicketCallBack callBack) {

            mTicketCallBack = null;
    }
    @Override
    public void getTicket(String title, String url, String cover) {
        onTicketLoading();
        mCover = cover;
        Retrofit retrofit = RetrofitManager.getInstance().getRetrofit();
        API api = retrofit.create(API.class);
        String ticketUrl = UrlUtils.getTicketUrl(url);
        TicketParams ticketParams = new TicketParams(ticketUrl, title);
        Call<TicketResult> task = api.GET_TICKET(ticketParams);
        task.enqueue(new Callback<TicketResult>() {
            @Override
            public void onResponse(Call<TicketResult> call, Response<TicketResult> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    mTicketResult = response.body();
                    if (mTicketCallBack != null) {
                        onLoadedSuccess();
                    }else{
                        onLoadedError();
                    }
                }
            }
            @Override
            public void onFailure(Call<TicketResult> call, Throwable t) {
                onLoadedError();
            }
        });
    }

    private void onLoadedSuccess() {
        if (mTicketCallBack != null) {
            mTicketCallBack.onTicketLoaded(mCover, mTicketResult);
        }
        else
            state = STATE.SUCCESS;
    }

    private void onLoadedError() {
        if (mTicketCallBack != null) {
            mTicketCallBack.onError();
        }else
            state = STATE.ERROR;
    }

    private void onTicketLoading() {
        if (mTicketCallBack != null) {
            mTicketCallBack.onLoading();
        } else
            state = STATE.LOADING;
    }
}
