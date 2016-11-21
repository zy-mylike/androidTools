package org.xndroid.cn.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xndroid.cn.database.sql.WhereBuilder;

import java.util.List;

/**
 * Created by Administrator on 2016/11/21 0021.
 */
public interface BaseDataEnery {

    Config getConfig();

    SQLiteDatabase getDatabase();

    void insert(BaseBean bean);

    void insert(List<BaseBean> been);

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

    Cursor execQuerySQL(String s);

    /**
     * delete table
     *
     * @param entityType
     */
    void dropTable(Class<? extends BaseBean> entityType);

    void addColumn(Class<? extends BaseBean> entityType, String column, Class dataType);
}
