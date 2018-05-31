package com.skyward.android.sdk.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 *
 * @author skyward
 * @date 2016/5/18
 * 普通字段存放地址
 */
public class PreferencesUtils {
    /**
     * 取出SharedPreferences中field字段对应的String类型的值
     * @param context
     * @param preName
     * @param field
     * @return
     */
    public static String getSharePreStr(Context context, String preName , String field){
        SharedPreferences preferences = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
       //如果该字段没对应值，则取出字符串0
        return preferences.getString(field,"");
    }

    /**
     * 取出SharedPreferences中field字段对应的int类型的值
     * @param context
     * @param preName
     * @param field
     * @return
     */
    public static int getSharePreInt(Context context, String preName , String field){
        SharedPreferences preferences = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
       //如果该字段没对应值，则取出0
        return preferences.getInt(field,0);
    }

    /**
     * 取出SharedPreferences中field字段对应的boolean类型的值
     * @param context
     * @param preName
     * @param field
     * @return
     */
    public static boolean getSharePreBoolean(Context context, String preName , String field){
        SharedPreferences preferences = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
      //如果该字段没对应值，则取出0
        return preferences.getBoolean(field,false);
    }

    /**
     * 保存string类型的value到SharedPreferences中的field字段
     * @param context
     * @param preName
     * @param field
     * @param value
     */
    public static void putSharePre(Context context, String preName , String field, String value){
        SharedPreferences preferences = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
        preferences.edit().putString(field,value).apply();
    }

    /**
     * 保存int类型的value到SharedPreferences中的field字段
     * @param context
     * @param preName
     * @param field
     * @param value
     */
    public static void putSharePre(Context context, String preName , String field, int value){
        SharedPreferences preferences = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
        preferences.edit().putInt(field,value).apply();
    }

    /**
     * 保存boolean类型的value到SharedPreferences中的field字段
     * @param context
     * @param preName
     * @param field
     * @param value
     */
    public static void putSharePre(Context context, String preName , String field, Boolean value){
        SharedPreferences preferences = context.getSharedPreferences(preName, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(field,value).apply();
    }

    /**
     * 清空保存的数据
     * @param mContext
     * @param preName
     */
    public static void clearSharePre(Context mContext, String preName){
        try {
            SharedPreferences preferences=mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
            preferences.edit().clear().apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 清空保存的数据
     * @param mContext
     * @param preName
     */
    public static void clearSharePre(Context mContext, String preName, String filed){
        try {
            SharedPreferences preferences=mContext.getSharedPreferences(preName, Context.MODE_PRIVATE);
            preferences.edit().remove(filed).apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
