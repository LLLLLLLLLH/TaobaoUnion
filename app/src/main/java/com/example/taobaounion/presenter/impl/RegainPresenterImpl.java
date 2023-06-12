package com.example.taobaounion.presenter.impl;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.RegainInfo;
import com.example.taobaounion.model.bean.RegisterInfo;
import com.example.taobaounion.presenter.IRegainPresenter;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.view.IRegainCallBack;

import java.net.HttpURLConnection;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class RegainPresenterImpl implements IRegainPresenter {

    private IRegainCallBack mCallBack;
    private API mApi;

    @Override
    public void registerViewCallBack(IRegainCallBack callBack) {
        mCallBack = callBack;
        Retrofit retrofit = RetrofitManager.getInstance().getBlogRetrofit();
        mApi = retrofit.create(API.class);
    }

    @Override
    public void unregisterViewCallBack(IRegainCallBack callBack) {
        mCallBack = callBack;
    }

    //检查手机号和手机验证码
    @Override
    public void checkSmsCode(RegisterInfo.Check info) {
        Call<ResponseBody> task = mApi.GET_CHECK_SMS_CODE(info.getPhone(), info.getSmsCode());
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    mCallBack.isCheck(true);
                } else
                    ToastUtils.showToast(response.message());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                ToastUtils.showToast("检查验证码失败");
            }
        });
    }

    //获取修改密码需要的手机验证码
    @Override
    public void getPhoneVerifyCode(RegisterInfo.Send info) {
        Call<ResponseBody> task = mApi.POST_FORGET_SEND_SMS(info);
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
                ToastUtils.showToast("获取手机验证码失败");
            }
        });
    }

    @Override
    public void modify(RegainInfo.modify info) {
        Call<ResponseBody> task = mApi.PUT_MODIFY_PWD(info);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    ToastUtils.showToast(response.message());
                    mCallBack.isModify(true);
                } else {
                    ToastUtils.showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                ToastUtils.showToast("发生错误");
            }
        });
    }

    @Override
    public void getPasswordBySms(String smsCode, RegainInfo.forget info) {
        Call<ResponseBody> task = mApi.PUT_GET_PASSWORD_BY_SMS(smsCode, info);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == HttpURLConnection.HTTP_OK && response.isSuccessful()) {
                    mCallBack.isGetPassword(true);
                    ToastUtils.showToast(response.message());
                } else {
                    ToastUtils.showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                ToastUtils.showToast("找回失败");
            }
        });
    }
}
