package com.example.taobaounion.base;

import android.app.Activity;
import android.app.Application;
import android.content.Context;

import com.tamsiree.rxkit.RxTool;
import com.tamsiree.rxui.activity.ActivityWebView;
import com.tencent.mmkv.MMKV;

public class BaseApplication extends Application {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getBaseContext();
        MMKV.initialize(this);
        RxTool.init(this);
    }

    public static Context getAppContext()
    {
        return mContext;
    }
}
