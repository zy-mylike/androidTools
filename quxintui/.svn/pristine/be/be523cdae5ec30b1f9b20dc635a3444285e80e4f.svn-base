package com.enetic.push;

import org.eteclab.track.TrackApplication;


/**
 * Created by json on 2016/3/31.
 */
public class PushApplication extends TrackApplication {

    /**
     * 全局上下文
     */

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler handler = CrashHandler.getInstance();
        handler.init(getApplicationContext());
        setHttpSuccessCode("1000");
    }

    /**
     * 应用结束 回收收尾操作
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    public String channelId() {
        return "a00000";
    }
}