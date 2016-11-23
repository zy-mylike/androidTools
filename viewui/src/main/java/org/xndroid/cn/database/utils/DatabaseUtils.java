package org.xndroid.cn.database.utils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xndroid.cn.BaseApplication;
import org.xndroid.cn.database.BaseBean;
import org.xndroid.cn.database.Config;
import org.xndroid.cn.database.sql.ColumnDbType;
import org.xndroid.cn.database.sql.ColumnTypeUtils;
import org.xndroid.cn.utils.Reflection;

import java.io.File;
import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class DatabaseUtils {

    public static SQLiteDatabase openOrCreateDatabase(Config config) {
        SQLiteDatabase result = null;
        File dbDir = new File(config.getDbDir());
        if (dbDir != null && (dbDir.exists() || dbDir.mkdirs())) {
            File dbFile = new File(dbDir, config.getDbName());
            result = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
        } else {
            result = BaseApplication.getApplication().openOrCreateDatabase(config.getDbName(), 0, null);
        }
        return result;

    }

    public static Object getBean(Class<? extends BaseBean> entityType, Cursor cursor) {
        Object t = Reflection.generateObject(entityType);
        try {

            Field[] fileAll = Reflection.getAllDeclaredFields(entityType);
            for (Field field : fileAll) {
                field.setAccessible(true);
                int index = cursor.getColumnIndex(field.getName());
                if (ColumnDbType.TEXT.equals(ColumnTypeUtils.getFileType(field))) {
                    String var = cursor.getString(index);
                    field.set(t, var);
                } else if (ColumnDbType.BLOB.equals(ColumnTypeUtils.getFileType(field))) {
                    field.set(t, cursor.getBlob(index));
                } else if (ColumnDbType.DOUBLE.equals(ColumnTypeUtils.getFileType(field))) {
                    field.setDouble(t, Double.valueOf(cursor.getDouble(index)));
                } else if (ColumnDbType.FLOAT.equals(ColumnTypeUtils.getFileType(field))) {
                    field.setFloat(t, Float.valueOf(cursor.getFloat(index)));
                } else if (ColumnDbType.INTEGER.equals(ColumnTypeUtils.getFileType(field))) {
                    int var = cursor.getInt(index);
                    field.setInt(t, Integer.valueOf(var));
                } else if (ColumnDbType.LONG.equals(ColumnTypeUtils.getFileType(field))) {
                    field.setLong(t, Long.valueOf(cursor.getLong(index)));
                } else {
                    field.set(t, String.valueOf(cursor.getString(index)));
                }
            }
            return t;
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;

    }
}
