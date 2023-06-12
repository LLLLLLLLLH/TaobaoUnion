package com.example.taobaounion.utils;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    private static final RetrofitManager instance = new RetrofitManager();
    private final Retrofit mRetrofit;
    private final Retrofit mBlogRetrofit;
    private final OkHttpClient mClient;

    public static RetrofitManager getInstance() {
        return instance;
    }

    private RetrofitManager() {
        mClient = new OkHttpClient.Builder()
                .addInterceptor(new CookieManager())
                .build();
        mRetrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mBlogRetrofit = new Retrofit.Builder()
                .client(mClient)
                .baseUrl(Constants.SUNOFBEACHES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return mRetrofit;
    }

    public Retrofit getBlogRetrofit() {
        return mBlogRetrofit;
    }

    public OkHttpClient getClient() {
        return mClient;
    }
}

