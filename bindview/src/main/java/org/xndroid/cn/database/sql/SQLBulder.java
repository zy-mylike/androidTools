package org.xndroid.cn.database.sql;

import android.text.TextUtils;

import org.xndroid.cn.database.BaseBean;
import org.xndroid.cn.utils.Reflection;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class SQLBulder {
    private SQLBulder() {
    }

    public static String CREATETABLE(Class<? extends BaseBean> clazz) {
        StringBuilder builder = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        builder.append("\'" + clazz.getSimpleName() + "\' (");


        Field[] fieldAll = Reflection.getAllDeclaredFields(clazz);
        if (fieldAll != null && fieldAll.length > 0) {
            for (Field field : fieldAll) {
                String name = field.getName();
                if (!name.equals("shadow$_klass_") && !name.equals("shadow$_monitor_") && !"serialVersionUID".equals(name) && !"$change".equals(name)) {
                    builder.append("\'" + name + "\' ");
                    if (name.equals("ids"))
                        builder.append(ColumnTypeUtils.getFileType(field) + " PRIMARY KEY AUTOINCREMENT,");
                    else
                        builder.append(ColumnTypeUtils.getFileType(field) + ",");
                }
            }
        }
        builder.deleteCharAt(builder.length() - 1);
        builder.append(")");
        return builder.toString();
    }

    public static String insertSQL(BaseBean bean) {
        StringBuilder builder = new StringBuilder("INSERT INTO ");
        builder.append("\'" + bean.getClass().getSimpleName() + "\' ( ");
        Field[] fieldAll = Reflection.getAllDeclaredFields(bean.getClass());
        if (fieldAll != null && fieldAll.length > 0) {
            for (Field field : fieldAll) {
                String name = field.getName();
                if (!name.equals("shadow$_klass_") && !name.equals("shadow$_monitor_") && !"serialVersionUID".equals(name) && !"$change".equals(name) && !"ids".equals(name)) {
                    builder.append("\'" + name + "\',");
                }
            }
            builder.deleteCharAt(builder.length() - 1);
            builder.append(") VALUES ( ");
            for (Field field : fieldAll) {
                String name = field.getName();
                if (!name.equals("shadow$_klass_") && !name.equals("shadow$_monitor_") && !"serialVersionUID".equals(name) && !"$change".equals(name) && !"ids".equals(name)) {
                    try {
                        String value = String.valueOf(field.get(bean));
                        if (ColumnDbType.TEXT == ColumnTypeUtils.getFileType(field)) {
                            builder.append((TextUtils.isEmpty(value) || "null".equals(value) ? "\'\'" : " \'" + value + "\'") + " ,");
                        } else {
                            builder.append((TextUtils.isEmpty(value) || "null".equals(value) ? "\'\'" : value) + ",");
                        }

                    } catch (IllegalAccessException e) {
                        builder.append("null" + ",");
                    }
                }
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        builder.append(")");
        return builder.toString();
    }

    public static String deleteWhereSQL(Class<? extends BaseBean> obj, String where) {
        StringBuilder builder = new StringBuilder("DELETE FROM ");
        builder.append("\'" + obj.getSimpleName() + "\'");
        if (!TextUtils.isEmpty(where))
            builder.append(" WHERE " + where);
        return builder.toString();
    }

    public static String selectWhereSQL(Class<? extends BaseBean> clz, String where) {
        StringBuilder builder = new StringBuilder("SELECT * FROM ");
        builder.append("\'" + clz.getSimpleName() + "\'");
        if (!TextUtils.isEmpty(where))
            builder.append(" WHERE " + where);
        return builder.toString();
    }

    public static String dropTableSQL(Class<? extends BaseBean> clz) {
        StringBuilder builder = new StringBuilder("DROP TABLE ");
        builder.append("\'" + clz.getSimpleName() + "\'");
        return builder.toString();
    }

    public static String addColumnSQL(Class<? extends BaseBean> clz, String columnName, Class object) {
        StringBuilder builder = new StringBuilder("ALTER TABLE ");
        builder.append("\'" + clz.getSimpleName() + "\'");
        builder.append(" ADD COLUMN " + columnName + " " + ColumnTypeUtils.getFileType(object.getName()));
        return builder.toString();
    }

    public static String updataWhereSQL(BaseBean obj, String where) {
        StringBuilder builder = new StringBuilder("UPDATE ");
        builder.append("\'" + obj.getClass().getSimpleName() + "\' SET \n");
        Field[] fieldAll = Reflection.getAllDeclaredFields(obj.getClass());
        if (fieldAll != null && fieldAll.length > 0) {
            for (Field field : fieldAll) {
                String name = field.getName();
                try {
                    if (!name.equals("shadow$_klass_") && !name.equals("shadow$_monitor_") && !"serialVersionUID".equals(name) && !"$change".equals(name) && !"$ids".equals(name)) {
                        builder.append("'" + name + "' = \'" + field.get(obj) + "\',");
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            builder.deleteCharAt(builder.length() - 1);
        }
        if (!TextUtils.isEmpty(where)) {
            builder.append(" WHERE ").append(where);
        }

        return builder.toString();
    }
}
