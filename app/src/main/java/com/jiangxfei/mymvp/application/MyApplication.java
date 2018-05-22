package com.jiangxfei.mymvp.application;

import android.app.Application;
import android.content.Context;

import com.jiangxfei.mymvp.util.SharedPreferencesUtil;

/**
 * @author: JiangXFei
 * @date: 2018/1/8 0008
 * @content:
 */

public class MyApplication extends Application {
   private static Context context;

    public static Context getContext() {
        return context;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        context=this;
        SharedPreferencesUtil.init(context);
    }
}
