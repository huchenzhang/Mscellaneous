package com.example.a15910.mscellaneous;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import org.litepal.LitePal;

import java.util.LinkedList;
import java.util.List;

/**
 * application
 * Created by hu on 2018/4/16.
 */

public class App extends Application {

    public static Context context;
    // 退出应用程序,销毁Activity相关
    public static List<Activity> activitys = new LinkedList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        //初始化数据库工具
        LitePal.initialize(this);
    }

    /***
     * 获取上下文
     * @return 上下文
     */
    public static Context getContextObject() {
        return context;
    }

    /**
     * 将activity添加到集合中
     * @param activity activity
     */
    public void addActivity(Activity activity) {
        //集合里不存在该activity
        if(!activitys.contains(activity)){
            activitys.add(activity);
        }
    }

    /**
     * 将activity移出集合
     * @param activity activity
     */
    public void removeActivity(Activity activity) {
        if (activitys.contains(activity)) {
            activitys.remove(activity);
        }
    }

    /**
     * 退出应用程序
     */
    public void clearAllActivity() {
        for (Activity activity : activitys) {
            if (activity != null) {
                activity.finish();
            }
        }
    }
}
