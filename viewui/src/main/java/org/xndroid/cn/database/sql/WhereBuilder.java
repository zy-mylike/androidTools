package org.xndroid.cn.database.sql;

import android.text.TextUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public class WhereBuilder {

    private final List<String> whereItems;

    private WhereBuilder() {
        whereItems = new ArrayList<>();
    }

    /**
     * create new instance
     *
     * @return
     */
    public static WhereBuilder w() {
        return new WhereBuilder();
    }


    /**
     * create new instance
     *
     * @param columnName
     * @param op         operator: "=","LIKE","IN","BETWEEN"...
     * @param value
     * @return
     */
    public static WhereBuilder w(String columnName, String op, Object value) {
        WhereBuilder result = new WhereBuilder();
        result.appendCondition(null, columnName, op, value);
        return result;
    }

    /**
     * add OR condition
     *
     * @param columnName
     * @param op         operator: "=","LIKE","IN","BETWEEN"...
     * @param value
     * @return
     */
    public WhereBuilder or(String columnName, String op, Object value) {
        appendCondition(whereItems.size() == 0 ? null : "OR", columnName, op, value);
        return this;
    }

    /**
     * add OR condition
     *
     * @param where expr("[OR] (" + where.toString() + ")")
     * @return
     */
    public WhereBuilder or(WhereBuilder where) {
        String condition = whereItems.size() == 0 ? " " : "OR ";
        return expr(condition + "(" + where.toString() + ")");
    }

    /**
     * create new instance
     *
     * @param columnName
     * @param op         operator: "=","LIKE","IN","BETWEEN"...
     * @param value
     * @return
     */
    public static WhereBuilder b(String columnName, String op, Object value) {
        WhereBuilder result = new WhereBuilder();
        result.appendCondition(null, columnName, op, value);
        return result;
    }

    /**
     * add AND condition
     *
     * @param columnName
     * @param op         operator: "=","LIKE","IN","BETWEEN"...
     * @param value
     * @return
     */
    public WhereBuilder and(String columnName, String op, Object value) {
        appendCondition(whereItems.size() == 0 ? null : "AND", columnName, op, value);
        return this;
    }


    public WhereBuilder expr(String expr) {
        whereItems.add(" " + expr);
        return this;
    }

    @Override
    public String toString() {
        if (whereItems.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String item : whereItems) {
            sb.append(item);
        }
        return sb.toString();
    }

    private void appendCondition(String conj, String columnName, String op, Object value) {
        StringBuilder builder = new StringBuilder();

        if (whereItems.size() > 0) {
            builder.append(" ");
        }

        // append conj
        if (!TextUtils.isEmpty(conj)) {
            builder.append(conj).append(" ");
        }

        // append columnName
        builder.append("\"").append(columnName).append("\"");

        // convert op
        if ("!=".equals(op)) {
            op = "<>";
        } else if ("==".equals(op)) {
            op = "=";
        }

        // append op & value
        if (value == null) {
            if ("=".equals(op)) {
                builder.append(" IS NULL");
            } else if ("<>".equals(op)) {
                builder.append(" IS NOT NULL");
            } else {
                builder.append(" ").append(op).append(" NULL");
            }
        } else {
            builder.append(" ").append(op).append(" ");

            if ("IN".equalsIgnoreCase(op)) {
                Iterable<?> items = null;
                if (value instanceof Iterable) {
                    items = (Iterable<?>) value;
                } else if (value.getClass().isArray()) {
                    int len = Array.getLength(value);
                    List<Object> arrayList = new ArrayList<Object>(len);
                    for (int i = 0; i < len; i++) {
                        arrayList.add(Array.get(value, i));
                    }
                    items = arrayList;
                }
                if (items != null) {
                    StringBuilder inSb = new StringBuilder("(");
                    for (Object item : items) {
                        Object itemColValue = item;
                        if (ColumnDbType.TEXT.equals(ColumnTypeUtils.getFileType(item))) {
                            String valueStr = itemColValue.toString();
                            if (valueStr.indexOf('\'') != -1) { // convert single quotations
                                valueStr = valueStr.replace("'", "''");
                            }
                            inSb.append("'").append(valueStr).append("'");
                        } else {
                            inSb.append(itemColValue);
                        }
                        inSb.append(",");
                    }
                    inSb.deleteCharAt(inSb.length() - 1);
                    inSb.append(")");
                    builder.append(inSb.toString());
                } else {
                    throw new IllegalArgumentException("value must be an Array or an Iterable.");
                }
            } else if ("BETWEEN".equalsIgnoreCase(op)) {
                Iterable<?> items = null;
                if (value instanceof Iterable) {
                    items = (Iterable<?>) value;
                } else if (value.getClass().isArray()) {
                    int len = Array.getLength(value);
                    List<Object> arrayList = new ArrayList<Object>(len);
                    for (int i = 0; i < len; i++) {
                        arrayList.add(Array.get(value, i));
                    }
                    items = arrayList;
                }
                if (items != null) {
                    Iterator<?> iterator = items.iterator();
                    if (!iterator.hasNext())
                        throw new IllegalArgumentException("value must have tow items.");
                    Object start = iterator.next();
                    if (!iterator.hasNext())
                        throw new IllegalArgumentException("value must have tow items.");
                    Object end = iterator.next();
                    if (ColumnDbType.TEXT.equals(ColumnTypeUtils.getFileType(start))) {
                        String startStr = start.toString();
                        if (startStr.indexOf('\'') != -1) { // convert single quotations
                            startStr = startStr.replace("'", "''");
                        }
                        String endStr = end.toString();
                        if (endStr.indexOf('\'') != -1) { // convert single quotations
                            endStr = endStr.replace("'", "''");
                        }
                        builder.append("'").append(startStr).append("'");
                        builder.append(" AND ");
                        builder.append("'").append(endStr).append("'");
                    } else {
                        builder.append(start);
                        builder.append(" AND ");
                        builder.append(end);
                    }
                } else {
                    throw new IllegalArgumentException("value must be an Array or an Iterable.");
                }
            } else {
                if (ColumnDbType.TEXT.equals(ColumnTypeUtils.getFileType(value))) {
                    String valueStr = value.toString();
                    if (valueStr.indexOf('\'') != -1) { // convert single quotations
                        valueStr = valueStr.replace("'", "''");
                    }
                    builder.append("'").append(valueStr).append("'");
                } else {
                    builder.append(value);
                }
            }
        }
        whereItems.add(builder.toString());
    }
}
