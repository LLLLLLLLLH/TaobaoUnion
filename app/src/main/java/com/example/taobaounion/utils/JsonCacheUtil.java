package com.example.taobaounion.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.taobaounion.base.BaseApplication;
import com.example.taobaounion.model.bean.CacheWithDuration;
import com.google.gson.Gson;

public class JsonCacheUtil {
    public static final String JSON_CACHE_SP_NAME="json_cache_sp_name";
    private final SharedPreferences mSharedPreferences;
    private final Gson mGson;

    private JsonCacheUtil(){
        mSharedPreferences= BaseApplication.getAppContext().getSharedPreferences(JSON_CACHE_SP_NAME, Context.MODE_PRIVATE);
        mGson=new Gson();
    }
    public void saveCache(String key,Object value){
        this.saveCache(key,value,-1);
    }

    public void saveCache(String key,Object value,long duration){
        SharedPreferences.Editor edit = mSharedPreferences.edit();
        String valueStr = mGson.toJson(value);//将Histories对象转成json字符串
        LogUtils.d(this,"value的值-->"+value+"valueStr的值-->"+valueStr);
        if(duration!=-1){
            //当前的时间
            duration+=System.currentTimeMillis();
        }
        //保存一个有数据有时间的内容 （也是将其先合成一个对象然后转成json字符串）
        CacheWithDuration cacheWithDuration = new CacheWithDuration(duration, valueStr);
        String cacheWithTime = mGson.toJson(cacheWithDuration);
        LogUtils.d(this,"cacheWithTime-->"+cacheWithTime);
        edit.putString(key,cacheWithTime);
        edit.apply();

    }

    public void delCache(String key){
        mSharedPreferences.edit().remove(key).apply();

    }

    //获取之前保存的数据并转成对象
    public  <T> T getValue(String key,Class<T> clazz) {
        String valueWithDuration = mSharedPreferences.getString(key, null);
        //如果没有则直接返回null
        if (valueWithDuration == null) {
            return null;
        }
        //有的话再将其json字符串转成对象
        CacheWithDuration cacheWithDuration = mGson.fromJson(valueWithDuration, CacheWithDuration.class);
        //对时间进行判断
        long duration = cacheWithDuration.getDuration();
        if (duration != -1 && duration - System.currentTimeMillis() < 0) {
            //判断是否过期
            //过期了
            return null;
        } else {
            //没过期
            String cache = cacheWithDuration.getCache();//获取搜索内容
            LogUtils.d(this,"cache-->"+cache);
            T result = mGson.fromJson(cache, clazz);//将搜索内容转成对象
            return result;

        }

    }

    private static JsonCacheUtil sJsonCacheUtil=null;

    public static JsonCacheUtil getInstance(){
        if(sJsonCacheUtil==null){
            sJsonCacheUtil=new JsonCacheUtil();
        }
        return sJsonCacheUtil;
    }
}

