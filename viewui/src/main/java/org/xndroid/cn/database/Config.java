package org.xndroid.cn.database;

import android.os.Build;

import org.xndroid.cn.BaseApplication;
import org.xndroid.cn.database.call.DataVersionChangListener;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class Config {

    private String mDBname = "org.db";
    private String mDir;
    private int mVersion = 1;
    private DataVersionChangListener versionChangListener;

    public Config() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mDir = BaseApplication.getApplication().getDataDir().getAbsolutePath();
        } else {
            mDir = "/data/data/" + BaseApplication.getApplication().getPackageName() + "/database";
        }
    }

    public void setVersionChangListener(DataVersionChangListener versionChangListener) {
        this.versionChangListener = versionChangListener;
    }

    public DataVersionChangListener getVersionChangListener() {
        return versionChangListener;
    }

    public int getVersion() {
        return mVersion;
    }

    /**
     * @param mVersion
     * @return
     */
    public Config setVersion(int mVersion) {
        this.mVersion = mVersion;
        return this;
    }

    public String getDbName() {
        return mDBname;
    }

    /**
     * @param mDBname
     * @return
     */
    public Config setDBname(String mDBname) {
        this.mDBname = mDBname;
        return this;
    }

    public String getDbDir() {
        return mDir;
    }

    /**
     * @param mDir
     * @return
     */
    public Config setDir(String mDir) {
        this.mDir = mDir;
        return this;
    }

}
