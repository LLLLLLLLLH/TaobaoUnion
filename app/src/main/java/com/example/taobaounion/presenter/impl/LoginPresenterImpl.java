package com.example.taobaounion.presenter.impl;

import androidx.annotation.NonNull;

import com.example.taobaounion.model.API;
import com.example.taobaounion.model.bean.CheckTokenResult;
import com.example.taobaounion.model.bean.LoginInfo;
import com.example.taobaounion.model.bean.ResponseData;
import com.example.taobaounion.model.bean.UserAchievement;
import com.example.taobaounion.model.bean.UserInfo;
import com.example.taobaounion.presenter.ILoginPresenter;
import com.example.taobaounion.utils.AppMd5Utils;
import com.example.taobaounion.utils.LogUtils;
import com.example.taobaounion.utils.RetrofitManager;
import com.example.taobaounion.utils.ToastUtils;
import com.example.taobaounion.utils.UserInfoUtils;
import com.example.taobaounion.view.ILoginCallBack;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class LoginPresenterImpl implements ILoginPresenter {

    private final API mApi;
    private ILoginCallBack mCallBack;
    private String mId;


    public LoginPresenterImpl() {
        Retrofit retrofit = RetrofitManager.getInstance().getBlogRetrofit();
        mApi = retrofit.create(API.class);
    }

    @Override
    public void registerViewCallBack(ILoginCallBack callBack) {
        mCallBack = callBack;
    }

    @Override
    public void unregisterViewCallBack(ILoginCallBack callBack) {
        if (mCallBack != null) {
            mCallBack = callBack;
        }
    }

    //获取验证码
/*    @Override
    public void getCode() {
        String codeUrl = UrlUtils.getCodeUrl(System.currentTimeMillis());
        LogUtils.d(this, "----------->" + codeUrl);
        Call<ResponseBody> task = mApi.GET_CAPTCHA(codeUrl);
        task.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    mL_c_i = response.headers().get("l_c_i");
                    //获取本次加载出来的验证码
                    InputStream inputStream = Objects.requireNonNull(response.body()).byteStream();
                    mCallBack.showCode(inputStream);
                    LogUtils.d(this, "--------->" + mL_c_i);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                LogUtils.d(this, "---------->" + t);
            }
        });
    }*/

    //登录
    @Override
    public void doLogin(String phone, String password, String verifyCode) {
        password = AppMd5Utils.getMd5(password);
        LoginInfo loginInfo = new LoginInfo(phone, password);
        Call<ResponseData<String>> task = mApi.POST_LOGIN(verifyCode, loginInfo);
        task.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<String>> call, @NonNull Response<ResponseData<String>> response) {
                ResponseData<String> body = response.body();
                if (body != null && response.isSuccessful() && body.isSuccess()) {
                    //保存手机号
                    UserInfoUtils.savePhone(phone);
                    //登录成功检查token获取用户信息
                    checkToken();
                    ToastUtils.showToast(response.message());
                    mCallBack.onSuccess(true);
                } else {
                    String tips = body == null ? "登录失败" : "登录失败" + body.getMessage();
                    LogUtils.d(this, getClass().getName() + "-------->" + tips);
                    LogUtils.d(this, getClass().getName() + "-------->" + verifyCode);
                    ToastUtils.showToast(tips);
                    mCallBack.onSuccess(false);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseData<String>> call, @NonNull Throwable t) {
                LogUtils.d(this, "-------------->" + t);
                ToastUtils.showToast("登录出错");
            }
        });
    }

    //获取用户头像
    @Override
    public void setUserAvatar(String phone) {
        Call<ResponseData<String>> task = mApi.GET_AVATAR(phone.trim());
        task.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(@NonNull Call<ResponseData<String>> call, @NonNull Response<ResponseData<String>> response) {
                String avatarUrl = response.body().getData();
                if (response.code() == HttpURLConnection.HTTP_OK && avatarUrl != null) {
                    mCallBack.showAvatar(avatarUrl);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseData<String>> call, @NonNull Throwable t) {
                LogUtils.d(this, "获取头像失败---------->" + t);
                t.printStackTrace();
            }
        });
    }

    //检查token
    @Override
    public void checkToken() {
        Call<CheckTokenResult> task = mApi.CHECK_TOKEN();
        task.enqueue(new Callback<CheckTokenResult>() {
            @Override
            public void onResponse(Call<CheckTokenResult> call, Response<CheckTokenResult> response) {
                if (response.isSuccessful()) {
                    //token未过期就更新取得id同时更新用户信息
                    if (response.body().getData() != null) {
                        mId = response.body().getData().getId();
                        getUserInfo();
                    }
                } else {
                    //token过期更新用户信息
                    UserInfoUtils.clearCache();
                }
            }

            @Override
            public void onFailure(Call<CheckTokenResult> call, Throwable t) {
                t.printStackTrace();
                LogUtils.d(this, "checkToken------>出错");
            }
        });
    }

    //获取用户信息,先要检查token是否过期
    @Override
    public void getUserInfo() {
        Call<UserInfo> task = mApi.GET_USER_INFO(mId);
        task.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (response.code() == HttpURLConnection.HTTP_OK) {
                    UserInfo.DataBean data = response.body().getData();
                    UserInfoUtils.savaInfo(data);
                    //同时获取个人成就的信息
                    getAchievement();
                }
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
                UserInfoUtils.clearCache();
            }
        });
    }

    //退出登录
    @Override
    public void doLogout() {
        Call<ResponseData<String>> task = mApi.GET_LOGOUT();
        task.enqueue(new Callback<ResponseData<String>>() {
            @Override
            public void onResponse(Call<ResponseData<String>> call, Response<ResponseData<String>> response) {
                /*
                 * 如果退出成功就更新用户信息存储
                 * */
                if (response.code() == HttpURLConnection.HTTP_OK && response.isSuccessful()) {
                    UserInfoUtils.clearCache();
                    ToastUtils.showToast(response.message());
                    mCallBack.onSuccess(true);
                } else {
                    ToastUtils.showToast(response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseData<String>> call, Throwable t) {
                t.printStackTrace();
                ToastUtils.showToast("退出失败");
            }
        });
    }

    @Override
    public void getAchievement() {
        Call<UserAchievement> task = mApi.GET_ACHIEVEMENT();
        task.enqueue(new Callback<UserAchievement>() {
            @Override
            public void onResponse(Call<UserAchievement> call, Response<UserAchievement> response) {
                if (response.isSuccessful() && response.code() == HttpURLConnection.HTTP_OK) {
                    LogUtils.d(this, "Achievement----------->获取个人成就信息成功");
                    UserAchievement.DataBean data = response.body().getData();
                    UserInfoUtils.saveAchievement(data);
                } else {
                    LogUtils.d(this, "Achievement-------->获取个人成绩信息失败");
                }
            }

            @Override
            public void onFailure(Call<UserAchievement> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
