package com.jiangxfei.mymvp.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by 上官林超 on 2017/1/3.
 */

public class SharedPreferencesUtil {

    private static SharedPreferences sp;
    private static SharedPreferences.Editor edit;

    private SharedPreferencesUtil() {
    }

    public static void init(Context context,String fileName) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        edit = sp.edit();
    }
    public static void init(Context context) {
        sp = context.getSharedPreferences("fire", Context.MODE_PRIVATE);
        edit = sp.edit();
    }
    public static void putString(String key, String value) {
        if (edit != null) {
            edit.putString(key, value);
            edit.commit();
        }
    }

    public static String getString(String key, String defvalue) {
        if (sp != null) {
            String string = sp.getString(key, defvalue);
            return string;
        }
        return "";
    }

    public static void putBoolean(String key, Boolean value) {
        if (edit != null) {
            edit.putBoolean(key, value);
            edit.commit();
        }
    }

    public static Boolean getBoolean(String key, Boolean defvalue) {
        if (sp != null) {
            boolean aBoolean = sp.getBoolean(key, defvalue);
            return aBoolean;
        }
        return false;
    }
}
