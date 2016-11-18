package org.xndroid.cn;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class BaseApplication extends Application {
    private ArrayList<Activity> mActivities;
    private static  BaseApplication application;

    public static BaseApplication getApplication() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        mActivities = new ArrayList<>();
    }

    public void addActivity(Activity activity) {
        mActivities.add(activity);
    }

    public void removeActivity(Activity aty) {
        this.mActivities.remove(aty);
    }

    public void finishAllActivity() {
        for (Activity ac : mActivities) {
            ac.finish();
        }
        mActivities.clear();
    }
}
