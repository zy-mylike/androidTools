package org.xndroid.cn.database;

import android.content.Context;
import android.text.TextUtils;

public class DaoConfig {

    private Context context;
    private String dbName = "eteclab.db";
    private int dbVersion = 1;
    private DbUpgradeListener dbUpgradeListener;
    private String dbDir;

    public DaoConfig(Context context) {
        this.context = context.getApplicationContext();
    }

    public Context getContext() {
        return this.context;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String dbName) {
        if (!TextUtils.isEmpty(dbName)) {
            this.dbName = dbName;
        }

    }

    public int getDbVersion() {
        return this.dbVersion;
    }

    public void setDbVersion(int dbVersion) {
        this.dbVersion = dbVersion;
    }

    public DbUpgradeListener getDbUpgradeListener() {
        return this.dbUpgradeListener;
    }

    public void setDbUpgradeListener(DbUpgradeListener dbUpgradeListener) {
        this.dbUpgradeListener = dbUpgradeListener;
    }

    public String getDbDir() {
        return this.dbDir;
    }

    public void setDbDir(String dbDir) {
        this.dbDir = dbDir;
    }
}
