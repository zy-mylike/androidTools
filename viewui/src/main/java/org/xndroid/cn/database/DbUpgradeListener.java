package org.xndroid.cn.database;

import android.database.DatabaseUtils;

public interface DbUpgradeListener {
    void onUpgrade(DatabaseUtils var1, int var2, int var3);

    void onDowmgrade(DatabaseUtils var1, int var2, int var3);
}