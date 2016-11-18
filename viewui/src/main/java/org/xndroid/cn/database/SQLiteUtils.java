package org.xndroid.cn.database;

import android.text.TextUtils;

import org.xndroid.cn.utils.Reflection;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class SQLiteUtils {
    private SQLiteUtils() {
    }

    public static String selectSQL(Class clazz, String where) {
        StringBuilder sql = new StringBuilder("SELECT * FROM " + clazz.getSimpleName());
        if (!TextUtils.isEmpty(where)) {
            sql.append(" WHERE ").append(where);
        }

        return sql.toString();
    }

    public static String insertSQL(DataBase bean) {
        Class clazz = bean.getClass();
        Field[] all = Reflection.getAllDeclaredFields(clazz);
        StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(clazz.getSimpleName());
        sql.append(" (");
        Field[] var4 = all;
        int var5 = all.length;

        int var6;
        Field field;
        for (var6 = 0; var6 < var5; ++var6) {
            field = var4[var6];
            if (!"id".equals(field.getName()) && !"$change".equals(field.getName()) && !"serialVersionUID".equals(field.getName())) {
                field.setAccessible(true);
                sql.append(field.getName()).append(",");
            }
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(") VALUES (");
        var4 = all;
        var5 = all.length;

        for (var6 = 0; var6 < var5; ++var6) {
            field = var4[var6];

            try {
                if (!"_ids".equals(field.getName()) && !"$change".equals(field.getName()) && !"serialVersionUID".equals(field.getName())) {
                    String e = field.get(bean) + "";
                    sql.append("\'").append(e.replace("\'", "").replace("\"", "")).append("\',");
                }
            } catch (IllegalAccessException var9) {
                var9.printStackTrace();
            }
        }

        sql.deleteCharAt(sql.length() - 1);
        sql.append(")");
        return sql.toString();
    }

    public static String deleteSQL(Class<?> clazz, String where) {
        StringBuilder sql = new StringBuilder("DELETE FROM " + clazz.getSimpleName());
        if (!TextUtils.isEmpty(where)) {
            sql.append(" WHERE ").append(where);
        }

        return sql.toString();
    }

    public static String updateSQL(DataBase bean, String where) {
        Class clazz = bean.getClass();
        Field[] all = Reflection.getAllDeclaredFields(clazz);
        StringBuilder sql = new StringBuilder("UPDATE ");
        sql.append(clazz.getSimpleName());
        sql.append(" SET ");
        Field[] var5 = all;
        int var6 = all.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            Field field = var5[var7];
            field.setAccessible(true);

            try {
                if (field.get(bean) != null && !"$change".equals(field.getName()) && !"serialVersionUID".equals(field.getName())) {
                    String e = field.get(bean) + "";
                    sql.append(field.getName()).append(" = \'").append(e.replace("\'", "").replace("\"", "")).append("\',");
                }
            } catch (IllegalAccessException var10) {
                var10.printStackTrace();
            }
        }

        sql.deleteCharAt(sql.length() - 1);
        if (!TextUtils.isEmpty(where)) {
            sql.append(" WHERE ").append(where);
        }

        return sql.toString();
    }

    public static String createTableSQL(Class<?> clazz) {
        Field[] all = Reflection.getAllDeclaredFields(clazz);
        StringBuilder sql = new StringBuilder();
        sql.append("create table if not exists ").append(clazz.getSimpleName()).append("(");
        Field[] var3 = all;
        int var4 = all.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            Field field = var3[var5];
            field.setAccessible(true);
            String name = field.getName();
            String type = field.getGenericType().toString().toLowerCase();
            if (!"serialVersionUID".equals(name) && !"$change".equals(name)) {
                if ("_ids".equals(name)) {
                    sql.append(name).append(" INTEGER PRIMARY KEY AUTOINCREMENT , ");
                } else if (type.equals("float")) {
                    sql.append(name).append(" FLOAT , ");
                } else if (type.equals("long")) {
                    sql.append(name).append(" LONG , ");
                } else if (type.equals("int")) {
                    sql.append(name).append(" INTEGER , ");
                } else {
                    sql.append(name).append(" TEXT , ");
                }
            }
        }

        sql.deleteCharAt(sql.lastIndexOf(","));
        sql.append(")");
        return sql.toString();
    }

}
