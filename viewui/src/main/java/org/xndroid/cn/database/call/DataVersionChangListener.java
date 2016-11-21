package org.xndroid.cn.database.call;

import org.xndroid.cn.database.DatabaseDox;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public interface DataVersionChangListener {

    void onUpgrade(DatabaseDox var1, int newVersion, int oldVersion);

    void onLowgrade(DatabaseDox var1, int newVersion, int oldVersion);
}
