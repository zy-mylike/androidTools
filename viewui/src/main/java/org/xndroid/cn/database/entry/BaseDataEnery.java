package org.xndroid.cn.database.entry;

import org.xndroid.cn.database.BaseBean;

/**
 * Created by Administrator on 2016/11/21 0021.
 */
public abstract class BaseDataEnery implements Database$Entrys {

    public abstract void insert(BaseBean bean);

    BaseDataEnery() {
    }

}
