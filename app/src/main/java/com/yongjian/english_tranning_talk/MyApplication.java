package com.yongjian.english_tranning_talk;

import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by YONGJIAN on 2016/6/11 0011.
 */
public class MyApplication extends Application {

    private List<Activity> activityList = new LinkedList<Activity>();
    private static MyApplication instance;
    public static MyApplication getInstance()
    {
        if(null == instance)
            instance = new MyApplication();
        return instance;
    }
    public void addActivity(Activity activity)
    {
        activityList.add(activity);
    }
    public void exit()
    {
        for(Activity activity:activityList)
        {
            activity.finish();
        }
        System.exit(0);
    }
}
