package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.RegisterInfo;
import com.example.taobaounion.presenter.IRegisterPresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.view.IRegisterCallBack;

import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegisterPresenterImpl implements IRegisterPresenter {


    private final API mApi;
    private IRegisterCallBack mCallBack;
    private boolean mIsCheckResult;

    public RegisterPresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getBlogRetrofit();
        mApi = retrofit.create(API.class);
    }

    @Override
    public void registerViewCallBack(IRegisterCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void unregisterViewCallBack(IRegisterCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void getPhoneVerifyCode(RegisterInfo.Send info) {
        Call<ResponseBody> task = mApi.POST_JOIN_SEND_SMS(info);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    LogUtils.d(this, "验证码-------->" + response.code() + response.message());
                    ToastUtils.showToast(response.message());
                } else {
                    LogUtils.d(this, "---------->" + response.code());
                    ToastUtils.showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public void checkSmsCode(RegisterInfo.Check info) {
        Call<ResponseBody> task = mApi.GET_CHECK_SMS_CODE(info.getPhone(), info.getSmsCode());
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    mIsCheckResult = true;
                } else
                    mIsCheckResult = false;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                mIsCheckResult = false;
            }
        });
    }

    @Override
    public void register(String smsCode, RegisterInfo.Register info) {
        if (!mIsCheckResult) {
            ToastUtils.showToast("手机验证码有误");
            return;
        }
        Call<ResponseBody> task = mApi.POST_REGISTER(smsCode, info);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    LogUtils.d(this, "responseCode-------->" + response.code());
                    LogUtils.d(this, "注册成功");
                    mIsCheckResult = false;
                    //UserInfoUtils.savaInfo();
                    mCallBack.onSuccess(true);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
