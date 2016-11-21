package org.xndroid.cn.database.sql;

import android.text.TextUtils;

import org.xndroid.cn.database.sql.ColumnDbType;

import java.lang.reflect.Field;
import java.sql.Blob;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class ColumnTypeUtils {

    public static ColumnDbType getFileType(Field field) {
        if (field == null) {
            return ColumnDbType.valueOf("TEXT");
        }
        String typeName = field.getType().getName();
        return getColumDBType(typeName);
    }


    public static ColumnDbType getFileType(Object obj) {
        if (obj == null) {
            return ColumnDbType.valueOf("TEXT");
        }
        String typeName = obj.getClass().getName();
        return getColumDBType(typeName);
    }


    public static ColumnDbType getColumDBType(String typeName) {
        if (TextUtils.isEmpty(typeName)) {
            return ColumnDbType.valueOf("TEXT");
        }
        if (typeName.equals(int.class.getName()) || typeName.equals(Integer.class.getName())) {
            return ColumnDbType.valueOf("INTEGER");
        } else if (typeName.equals(Float.class.getName()) || typeName.equals(float.class.getName())) {
            return ColumnDbType.valueOf("FLOAT");
        } else if (typeName.equals(Double.class.getName()) || typeName.equals(double.class.getName())) {
            return ColumnDbType.valueOf("DOUBLE");
        } else if (typeName.equals(String.class.getName()) || typeName.equals(char.class.getName())) {
            return ColumnDbType.valueOf("TEXT");
        } else if (typeName.equals(Blob.class.getName())) {
            return ColumnDbType.valueOf("BLOB");
        } else if (typeName.equals(long.class.getName()) || typeName.equals(Long.class.getName())) {
            return ColumnDbType.valueOf("LONG");
        } else {
            return ColumnDbType.valueOf("TEXT");
        }
    }
}
