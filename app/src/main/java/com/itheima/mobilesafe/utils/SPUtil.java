package com.itheima.mobilesafe.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2018/5/7.
 */

public class SPUtil {
    private static final String CONFIG = "config";

    /**
     * 从config里获取boolean值
     *
     * @param context   上下文
     * @param key       KEY值
     * @param def       默认值
     * @return          获取到的或者默认值
     */
    public static boolean getBoolean(Context context,String key, boolean def){
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        def = sp.getBoolean(key, def);
        return def;
    }

    /**
     * 放置boolean值到config里
     *
     * @param context   上下文
     * @param key       KEY值
     * @param value     需要放入的boolean值
     */
    public static void putBoolean(Context context,String key, boolean value){
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, value).commit();
    }

    /**
     * 从config里获取String值
     *
     * @param context   上下文
     * @param key       KEY值
     * @param def       默认值
     * @return          返回获取到的或者默认值
     */
    public static String getString(Context context,String key, String def){
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        def = sp.getString(key, def);
        return def;
    }

    /**
     * 放置String值到config里
     *
     * @param context   上下文
     * @param key       KEY值
     * @param value     需要放入的String值
     */
    public static void putString(Context context,String key, String value){
        SharedPreferences sp = context.getSharedPreferences(CONFIG, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).commit();
    }
}
