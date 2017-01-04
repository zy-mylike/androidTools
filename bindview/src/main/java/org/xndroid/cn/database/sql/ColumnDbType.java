package org.xndroid.cn.database.sql;

/**
 * Created by Administrator on 2016/11/21 0021.
 */

public enum ColumnDbType {
    INTEGER("INTEGER"), DOUBLE("DOUBLE"), FLOAT("DOUBLE"), TEXT("TEXT"), BLOB("BLOB"), LONG("LONG");
    private String value;

    ColumnDbType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
