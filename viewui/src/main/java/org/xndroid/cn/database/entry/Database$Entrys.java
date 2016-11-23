package org.xndroid.cn.database.entry;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xndroid.cn.database.BaseBean;
import org.xndroid.cn.database.Config;
import org.xndroid.cn.database.sql.WhereBuilder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 0021.
 */
interface Database$Entrys {

    Config getConfig();

    SQLiteDatabase getDatabase();

    void insert(List<? extends BaseBean> been);

    <T extends BaseBean> List<T> selectAll(Class<T> entityType);

    <T extends BaseBean> List<T> selectWhere(Class<T> entityType, WhereBuilder builder);

    <T extends BaseBean> List<T> selectWhere(Class<T> entityType, String where);

    void deleteAll(Class<? extends BaseBean> entityType);

    void deleteWhere(BaseBean bean, WhereBuilder builder);

    void deleteWhere(BaseBean bean, String where);

    void delete(BaseBean bean);


    void updata(BaseBean bean);

    void updataWhere(BaseBean bean, String where);


    void execSQL(String s);

    void close();

    Cursor execQuerySQL(String s);

    /**
     * delete table
     *
     * @param entityType
     */
    void dropTable(Class<? extends BaseBean> entityType);

    void createTable(Class<? extends BaseBean> entityType);

    void addColumn(Class<? extends BaseBean> entityType, String column, Class dataType);
}
