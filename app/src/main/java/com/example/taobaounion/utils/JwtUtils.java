package com.example.taobaounion.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;

import com.example.taobaounion.base.BaseApplication;

public class JwtUtils {
    private static final String TOKEN_KEY = "token";

    /**
     * 保存加密后的token
     * @param context 上下文
     * @param token 要保存的token
     */
    public static void saveToken(Context context, String token) {
        SharedPreferences sp = context.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN_KEY, encryptToken(token));
        editor.apply();
    }

    /**
     * 获取解密后的token
     * @param context 上下文
     * @return 解密后的token
     */
    public static String getToken(Context context) {
        SharedPreferences sp = context.getSharedPreferences("jwt", Context.MODE_PRIVATE);
        String encryptedToken = sp.getString(TOKEN_KEY, null);
        return decryptToken(encryptedToken);
    }

    /**
     * 加密token
     * @param token 要加密的token
     * @return 加密后的token
     */
    private static String encryptToken(String token) {
        return Base64.encodeToString(token.getBytes(), Base64.DEFAULT);
    }

    /**
     * 解密token
     * @param encryptedToken 加密后的token
     * @return 解密后的token
     */
    private static String decryptToken(String encryptedToken) {
        if (encryptedToken == null) {
            return null;
        }
        byte[] data = Base64.decode(encryptedToken, Base64.DEFAULT);
        return new String(data);
    }

    public static void clearToken()
    {
        SharedPreferences sp = BaseApplication.getAppContext().getSharedPreferences("jwt", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.clear();
        edit.apply();
    }
}
