package org.xndroid.cn.database.entry;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xndroid.cn.database.BaseBean;
import org.xndroid.cn.database.Config;
import org.xndroid.cn.database.call.DataVersionChangListener;
import org.xndroid.cn.database.sql.SQLBulder;
import org.xndroid.cn.database.sql.WhereBuilder;
import org.xndroid.cn.database.utils.DatabaseUtils;
import org.xndroid.cn.utils.LogUtils;
import org.xndroid.cn.utils.Reflection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class DatabaseDox extends BaseDataEnery {

    private final static HashMap<Config, DatabaseDox> CONFIG_MAP = new HashMap();
    protected SQLiteDatabase sqLiteDatabase;
    private volatile boolean writeLocked = false;
    private volatile boolean allowTransaction = false;
    private Lock mLock = new ReentrantLock();
    protected Config mConfig;

    private DatabaseDox(Config config) {
        if (config == null) {
            new Throwable(new RuntimeException("dataBase config is null"));
        }

        this.mConfig = config;
        this.sqLiteDatabase = DatabaseUtils.openOrCreateDatabase(config);
        allowTransaction = true;
    }


    public static DatabaseDox getIntences(Config config) {
        if (config == null) {
            config = new Config();
        }

        DatabaseDox dox = CONFIG_MAP.get(config);
        if (dox == null) {
            dox = new DatabaseDox(config);
            CONFIG_MAP.put(config, dox);
        } else {
            dox.mConfig = config;
        }

        int newVersion = config.getVersion();
        int oldVersion = dox.sqLiteDatabase.getVersion();
        DataVersionChangListener l = config.getVersionChangListener();
        if (newVersion != oldVersion) {
            if (l != null) {
                if (newVersion > oldVersion) {
                    l.onUpgrade(dox, newVersion, oldVersion);
                } else {
                    l.onLowgrade(dox, newVersion, oldVersion);
                }
                dox.sqLiteDatabase.setVersion(newVersion);
            }
        }
        return dox;
    }


    @Override
    public Config getConfig() {
        return mConfig;
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return sqLiteDatabase;
    }


    @Override
    public void insert(BaseBean bean) {
        if (bean instanceof BaseBean) {
            beginTransaction();
            execSQL(SQLBulder.insertSQL(bean));
            setTransactionSuccessful();
            endTransaction();
        } else {
            throw new ClassCastException();
        }

    }

    @Override
    public void insert(List<? extends BaseBean> list) {
        beginTransaction();
        for (BaseBean bean : list)
            execSQL(SQLBulder.insertSQL(bean));
        setTransactionSuccessful();
        endTransaction();
    }

    @Override
    public <T extends BaseBean> List<T> selectAll(Class<T> entityType) {
        return selectWhere(entityType, "");
    }


    @Override
    public <T extends BaseBean> List<T> selectWhere(Class<T> entityType, WhereBuilder builder) {
        return selectWhere(entityType, builder.toString());
    }

    @Override
    public <T extends BaseBean> List<T> selectWhere(Class<T> entityType, String where) {
        beginTransaction();
        List<T> list = null;
        Cursor cursor = execQuerySQL(SQLBulder.selectWhereSQL(entityType, where));
        if (cursor != null) {
            cursor.moveToFirst();
            list = new ArrayList<T>();
            while (cursor.moveToNext()) {
                list.add((T) DatabaseUtils.getBean(entityType, cursor));
            }
            cursor.close();
        }
        setTransactionSuccessful();
        endTransaction();
        return list;
    }

    @Override
    public void deleteAll(Class<? extends BaseBean> entityType) {
        deleteWhere((BaseBean) Reflection.generateObject(entityType), (String) null);
    }


    @Override
    public void delete(BaseBean bean) {
        deleteWhere(bean, WhereBuilder.w().and("ids", "=", bean.ids));
    }


    @Override
    public void deleteWhere(BaseBean bean, WhereBuilder builder) {
        deleteWhere(bean, builder.toString());
    }

    @Override
    public void deleteWhere(BaseBean bean, String where) {
        beginTransaction();
        execSQL(SQLBulder.deleteWhereSQL(bean.getClass(), where));
        setTransactionSuccessful();
        endTransaction();
    }

    @Override
    public void updataWhere(BaseBean bean, String where) {
        beginTransaction();
        execSQL(SQLBulder.updataWhereSQL(bean, where));
        setTransactionSuccessful();
        endTransaction();
    }

    @Override
    public void updata(BaseBean bean) {
        beginTransaction();
        execSQL(SQLBulder.updataWhereSQL(bean, WhereBuilder.b("ids", "=", bean.ids).toString()));
        setTransactionSuccessful();
        endTransaction();
    }

    /**
     * delete table
     *
     * @param entityType
     */
    @Override
    public void dropTable(Class<? extends BaseBean> entityType) {
        beginTransaction();
        execSQL(SQLBulder.dropTableSQL(entityType));
        setTransactionSuccessful();
        endTransaction();
    }

    @Override
    public void createTable(Class<? extends BaseBean> entityType) {
        beginTransaction();
        execSQL(SQLBulder.CREATETABLE(entityType));
        setTransactionSuccessful();
        endTransaction();
    }

    @Override
    public void addColumn(Class<? extends BaseBean> entityType, String column, Class dataType) {
        beginTransaction();
        execSQL(SQLBulder.addColumnSQL(entityType, column, dataType));
        setTransactionSuccessful();
        endTransaction();
    }

    @Override
    public void execSQL(String s) {

        synchronized (mConfig) {
            sqLiteDatabase.execSQL(s);
            debug(s);
        }
    }

    private void debug(String s) {
        LogUtils.e(s);
    }

    @Override
    public void close() {
        sqLiteDatabase.close();
        CONFIG_MAP.remove(mConfig);
    }

    @Override
    public Cursor execQuerySQL(String s) {
        debug(s);
        return sqLiteDatabase.rawQuery(s, null);
    }

    private void beginTransaction() {
        if (allowTransaction) {
            sqLiteDatabase.beginTransaction();
        } else {
            mLock.lock();
            writeLocked = true;
        }

    }

    private void setTransactionSuccessful() {
        if (allowTransaction) {
            sqLiteDatabase.setTransactionSuccessful();
        }
    }

    private void endTransaction() {
        if (allowTransaction) {
            sqLiteDatabase.endTransaction();
        }
        if (this.writeLocked) {
            this.mLock.unlock();
            this.writeLocked = false;
        }
    }


}