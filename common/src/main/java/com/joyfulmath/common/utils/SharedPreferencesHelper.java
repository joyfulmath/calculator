package com.joyfulmath.common.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

/**
 * Created by demanlu on 2017/1/19.
 */

public class SharedPreferencesHelper {
    private static SharedPreferences mSharedPreferences = null;
    private static SharedPreferences.Editor mEdit = null;
    private static Context appContext;

    public static void init(Context context){
        appContext = context;
        mSharedPreferences = appContext.getSharedPreferences(SharedPreferencesHelper.class.getName(),
                Context.MODE_PRIVATE);
        mEdit = mSharedPreferences.edit();
    }

    public static void relese(){
        appContext = null;
        mSharedPreferences = null;
        mEdit = null;
    }

    public static SharedPreferences getSharedPreferences(){
        if(mSharedPreferences == null){
            throw new RuntimeException("should init first");
        }
        return mSharedPreferences;
    }

    public static SharedPreferences.Editor getSharedPreferencesEdit(){
        if(mEdit == null){
            throw new RuntimeException("should init first");
        }
        return mEdit;
    }

    /**
     * put json data
     * @param key
     * @param obj
     */
    public static void  saveObject(String key, Object obj) {
        if(obj == null){
            /**demanlu@20170120 if reset this key-value ,should using remove**/
            return;
        }

        try{
            Gson gson = new Gson();
            String toSave = gson.toJson(obj);
            mEdit.putString(key, toSave).apply();
        }catch (Exception e)
        {
            e.printStackTrace();
            TraceLog.w("SettingConstants", "Save key: " + key + "; value: " + obj.toString());
        }
    }

    /**
     * read json from java
     * @param key
     * @param cla
     * @param <T>
     * @return
     */
    public static <T> T getObject(String key, Class<T> cla) {

        try {
            Gson gson = new Gson();
            String temp = mSharedPreferences.getString(key,null);

            if (temp == null || temp.trim().length() == 0) {
                return null;
            }
            T result = gson.fromJson(temp, cla);
            return result;

        } catch (Exception e) {
            TraceLog.w("SettingConstants", "key:" + key);
        }

        return null;
    }
}
