package com.example.taobaounion.utils;

import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/*
 *     拦截器获取到图片验证码携带的cookie
 * */

public class CookieManager implements Interceptor {

    public static final String VERIFICATION_KEY = "verify_key_l_c_i";
    public static final String SOB_TOKEN = "sob_token";

    public Response intercept(@NonNull Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder newBuilder = originalRequest.newBuilder();

        String verifyKey = MMKV.defaultMMKV().decodeString(VERIFICATION_KEY);
        addHeaderIfNotEmpty(newBuilder, "l_c_i", verifyKey);

        String token = MMKV.defaultMMKV().decodeString(SOB_TOKEN);
        addHeaderIfNotEmpty(newBuilder, "sob_token", token);

        Response response = chain.proceed(newBuilder.build());
        handleHeaderResponse(response);

        return response;
    }

    private static void addHeaderIfNotEmpty(Request.Builder builder, String name, String value) {
        if (!TextUtils.isEmpty(value)) {
            Log.d("token", "     "+value);
            builder.addHeader(name, value);
        }
    }

    private static void handleHeaderResponse(Response response) {
        String sobToken = response.header("sob_token");
        if (!TextUtils.isEmpty(sobToken)) {
            MMKV.defaultMMKV().putString(SOB_TOKEN, sobToken);
        }

        String l_c_i = response.header("l_c_i");
        if (!TextUtils.isEmpty(l_c_i)) {
            MMKV.defaultMMKV().putString(VERIFICATION_KEY, l_c_i);
        }
    }
}
