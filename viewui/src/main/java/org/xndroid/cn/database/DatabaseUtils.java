package org.xndroid.cn.database;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.text.TextUtils;

import org.xndroid.cn.utils.LogUtils;
import org.xndroid.cn.utils.Reflection;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class DatabaseUtils {
    private static final HashMap<String, DatabaseUtils> daoMap = new HashMap();
    private SQLiteDatabase database;
    private DaoConfig daoConfig;
    public static boolean DEBUG = false;
    private boolean allowTransaction = false;
    private Lock writeLock = new ReentrantLock();
    private volatile boolean writeLocked = false;

    private DatabaseUtils(DaoConfig config) {
        if (config == null) {
            throw new IllegalArgumentException("daoConfig may not be null");
        } else {
            this.database = this.createDatabase(config);
            this.daoConfig = config;
        }
    }

    public static synchronized DatabaseUtils getInstance(DaoConfig daoConfig) {
        DatabaseUtils dao = daoMap.get(daoConfig.getDbName());
        if (dao == null) {
            dao = new DatabaseUtils(daoConfig);
            daoMap.put(daoConfig.getDbName(), dao);
        } else {
            dao.daoConfig = daoConfig;
        }

        SQLiteDatabase database = dao.database;
        int oldVersion = database.getVersion();
        int newVersion = daoConfig.getDbVersion();
        if (oldVersion != newVersion) {
            if (oldVersion != 0) {
                DbUpgradeListener upgradeListener = daoConfig.getDbUpgradeListener();
                if (upgradeListener != null && oldVersion != newVersion) {
                    Class[] classes = new Class[]{DatabaseUtils.class, Integer.class, Integer.class};
                    Object[] objects = new Object[]{dao, oldVersion, newVersion};
                    try {
                        upgradeListener.getClass().getMethod(oldVersion < newVersion ? "onUpgrade" : "onDowmgrade", classes).invoke(upgradeListener, objects);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            database.setVersion(newVersion);
        }

        return dao;
    }


    public DatabaseUtils configAllowTransaction(boolean allowTransaction) {
        this.allowTransaction = allowTransaction;
        return this;
    }

    public SQLiteDatabase getDatabase() {
        return this.database;
    }

    public DaoConfig getDaoConfig() {
        return this.daoConfig;
    }

    public void save(DataBase bean) {
        try {
            this.beginTransaction();
            this.createTable(bean.getClass());
            this.execNonQuery(SQLiteUtils.insertSQL(bean));
            this.setTransactionSuccessful();
        } finally {
            this.endTransaction();
        }

    }

    public void saveAll(List<? extends DataBase> entities) {
        if (entities != null && entities.size() != 0) {
            try {
                this.beginTransaction();
                this.createTable(((DataBase) entities.get(0)).getClass());
                Iterator var2 = entities.iterator();

                while (var2.hasNext()) {
                    DataBase bean = (DataBase) var2.next();
                    this.execNonQuery(SQLiteUtils.insertSQL(bean));
                }

                this.setTransactionSuccessful();
            } finally {
                this.endTransaction();
            }
        }
    }

    public void delete(DataBase bean) {
        try {
            this.beginTransaction();
            this.execNonQuery(SQLiteUtils.deleteSQL(bean.getClass(), "_ids = " + bean._ids));
            this.setTransactionSuccessful();
        } finally {
            this.endTransaction();
        }

    }

    public void delete(Class<?> beanType, String where) {
        try {
            this.beginTransaction();
            this.execNonQuery(SQLiteUtils.deleteSQL(beanType, where));
            this.setTransactionSuccessful();
        } finally {
            this.endTransaction();
        }

    }

    public void deleteAll(Class<?> clazz) {
        try {
            this.beginTransaction();
            this.execNonQuery(SQLiteUtils.deleteSQL(clazz, (String) null));
            this.setTransactionSuccessful();
        } finally {
            this.endTransaction();
        }

    }

    public void deleteAll(List<? extends DataBase> entities) {
        if (entities != null && entities.size() != 0) {
            try {
                this.beginTransaction();
                Iterator var2 = entities.iterator();

                while (var2.hasNext()) {
                    DataBase bean = (DataBase) var2.next();
                    this.execNonQuery(SQLiteUtils.deleteSQL(bean.getClass(), "_ids = " + bean._ids));
                }

                this.setTransactionSuccessful();
            } finally {
                this.endTransaction();
            }
        }
    }

    public void update(DataBase bean) {
        try {
            this.beginTransaction();
            this.execNonQuery(SQLiteUtils.updateSQL(bean, (String) null));
            this.setTransactionSuccessful();
        } finally {
            this.endTransaction();
        }

    }

    public void update(DataBase bean, String where) {
        try {
            this.beginTransaction();
            this.execNonQuery(SQLiteUtils.updateSQL(bean, where));
            this.setTransactionSuccessful();
        } finally {
            this.endTransaction();
        }

    }

    public void updateAll(List<? extends DataBase> entities) {
        if (entities != null && entities.size() != 0) {
            try {
                this.beginTransaction();
                Iterator var2 = entities.iterator();

                while (var2.hasNext()) {
                    DataBase bean = (DataBase) var2.next();
                    this.execNonQuery(SQLiteUtils.updateSQL(bean, (String) null));
                }

                this.setTransactionSuccessful();
            } finally {
                this.endTransaction();
            }
        }
    }

    public void updateAll(List<? extends DataBase> entities, String where) {
        if (entities != null && entities.size() != 0) {
            try {
                this.beginTransaction();
                Iterator var3 = entities.iterator();

                while (var3.hasNext()) {
                    DataBase bean = (DataBase) var3.next();
                    this.execNonQuery(SQLiteUtils.updateSQL(bean, where));
                }

                this.setTransactionSuccessful();
            } finally {
                this.endTransaction();
            }
        }
    }

    public <T> T findById(Class<T> clazz, String id) {
        Cursor cursor = this.execQuery(SQLiteUtils.selectSQL(clazz, "_ids = " + id));
        if (cursor != null) {
            Object var4;
            try {
                if (!cursor.moveToNext()) {
                    return null;
                }

                var4 = this.generateBean(cursor, clazz);
            } finally {
                cursor.close();
            }

            return (T) var4;
        } else {
            return null;
        }
    }

    public <T> List<T> findAll(Class<T> clazz, String where) {
        ArrayList result = new ArrayList();
        Cursor cursor = this.execQuery(SQLiteUtils.selectSQL(clazz, where));
        if (cursor != null) {
            try {
                while (cursor.moveToNext()) {
                    Object bean = this.generateBean(cursor, clazz);
                    result.add(bean);
                }
            } finally {
                cursor.close();
            }
        }

        return result;
    }

    private SQLiteDatabase createDatabase(DaoConfig config) {
        SQLiteDatabase result = null;
        String dbDir = config.getDbDir();
        if (!TextUtils.isEmpty(dbDir)) {
            File dir = new File(dbDir);
            if (dir.exists() || dir.mkdirs()) {
                File dbFile = new File(dbDir, config.getDbName());
                result = SQLiteDatabase.openOrCreateDatabase(dbFile, (CursorFactory) null);
            }
        } else {
            result = config.getContext().openOrCreateDatabase(config.getDbName(), 0, (CursorFactory) null);
        }

        return result;
    }

    public void createTable(Class<?> clazz) {
        this.execNonQuery(SQLiteUtils.createTableSQL(clazz));
    }

    public void dropTable(Class<?> clazz) {
        this.execNonQuery("DROP TABLE " + clazz.getSimpleName());
    }

    public void close() {
        String dbName = this.daoConfig.getDbName();
        if (daoMap.containsKey(dbName)) {
            daoMap.remove(dbName);
            this.database.close();
        }

    }

    private <T> T generateBean(Cursor cursor, Class<T> clazz) {
        Field[] all = Reflection.getAllDeclaredFields(clazz);
        Object item = Reflection.generateObject(clazz);
        Field[] var5 = all;
        int var6 = all.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Field f = var5[var7];
            f.setAccessible(true);
            String name = f.getName();
            String type = f.getGenericType().toString();

            try {
                if (!"$change".equals(name) && !"serialVersionUID".equals(name)) {
                    byte var12 = -1;
                    switch (type.hashCode()) {
                        case 104431:
                            if (type.equals("int")) {
                                var12 = 2;
                            }
                            break;
                        case 3327612:
                            if (type.equals("long")) {
                                var12 = 1;
                            }
                            break;
                        case 97526364:
                            if (type.equals("float")) {
                                var12 = 0;
                            }
                    }

                    switch (var12) {
                        case 0:
                            f.set(item, Float.valueOf(cursor.getFloat(cursor.getColumnIndex(name))));
                            break;
                        case 1:
                            f.set(item, Long.valueOf(cursor.getLong(cursor.getColumnIndex(name))));
                            break;
                        case 2:
                            f.set(item, Integer.valueOf(cursor.getInt(cursor.getColumnIndex(name))));
                            break;
                        default:
                            f.set(item, cursor.getString(cursor.getColumnIndex(name)));
                    }
                }
            } catch (Exception var13) {
                var13.printStackTrace();
            }
        }

        return (T) item;
    }

    private void debugSQL(String sql) {
        if (DEBUG) {
            LogUtils.i(sql);
        }

    }

    private void beginTransaction() {
        if (this.allowTransaction) {
            this.database.beginTransaction();
        } else {
            this.writeLock.lock();
            this.writeLocked = true;
        }

    }

    private void setTransactionSuccessful() {
        if (this.allowTransaction) {
            this.database.setTransactionSuccessful();
        }

    }

    private void endTransaction() {
        if (this.allowTransaction) {
            this.database.endTransaction();
        }

        if (this.writeLocked) {
            this.writeLock.unlock();
            this.writeLocked = false;
        }

    }

    public void execNonQuery(String sql) {
        this.debugSQL(sql);
        this.database.execSQL(sql);
    }

    public Cursor execQuery(String sql) {
        this.debugSQL(sql);
        return this.database.rawQuery(sql, (String[]) null);
    }

}
