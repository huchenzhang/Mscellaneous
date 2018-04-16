package com.example.a15910.mscellaneous;

import android.app.Application;
import android.content.Context;

import org.litepal.LitePal;

/**
 * application
 * Created by hu on 2018/4/16.
 */

public class App extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化数据库工具
        LitePal.initialize(this);
    }

    //返回
    public static Context getContextObject() {
        return context;
    }
}
