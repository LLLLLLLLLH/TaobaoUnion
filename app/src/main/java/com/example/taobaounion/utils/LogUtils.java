package com.example.taobaounion.utils;

import android.util.Log;

public class LogUtils {
    public static int currentLev = 4;
    public static final int DEBUG_LEV = 4;
    public static final int INFO_LEV = 3;
    public static final int WARNING_LEV = 2;
    public static final int ERROR_LEV = 1;


    public static void d(Object Object, Object log)
    {
        if (currentLev >= DEBUG_LEV)
            Log.d(Object.getClass().getSimpleName(),String.valueOf(log));
    }

    public static void i(Object Object,Object log)
    {
        if (currentLev >= INFO_LEV)
            Log.i(Object.getClass().getSimpleName(),String.valueOf(log));
    }
    public static void w(Object Object,Object log)
    {
        if (currentLev >= WARNING_LEV)
            Log.w(Object.getClass().getSimpleName(), String.valueOf(log));
    }
    public static void e(Object Object,Object log)
    {
        if (currentLev >= ERROR_LEV)
            Log.e(Object.getClass().getSimpleName(),String.valueOf(log));
    }

}
