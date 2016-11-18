package pay.eteclab.com.androidutils;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.xutils.DbManager;
import org.xutils.common.util.KeyValue;
import org.xutils.db.Selector;
import org.xutils.db.sqlite.SqlInfo;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.db.table.DbModel;
import org.xutils.db.table.TableEntity;
import org.xutils.ex.DbException;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class D implements DbManager {
    @Override
    public DaoConfig getDaoConfig() {
        return null;
    }

    @Override
    public SQLiteDatabase getDatabase() {
        return null;
    }

    @Override
    public boolean saveBindingId(Object entity) throws DbException {
        return false;
    }

    @Override
    public void saveOrUpdate(Object entity) throws DbException {

    }

    @Override
    public void save(Object entity) throws DbException {

    }

    @Override
    public void replace(Object entity) throws DbException {

    }

    @Override
    public void deleteById(Class<?> entityType, Object idValue) throws DbException {

    }

    @Override
    public void delete(Object entity) throws DbException {

    }

    @Override
    public void delete(Class<?> entityType) throws DbException {

    }

    @Override
    public int delete(Class<?> entityType, WhereBuilder whereBuilder) throws DbException {
        return 0;
    }

    @Override
    public void update(Object entity, String... updateColumnNames) throws DbException {

    }

    @Override
    public int update(Class<?> entityType, WhereBuilder whereBuilder, KeyValue... nameValuePairs) throws DbException {
        return 0;
    }

    @Override
    public <T> T findById(Class<T> entityType, Object idValue) throws DbException {
        return null;
    }

    @Override
    public <T> T findFirst(Class<T> entityType) throws DbException {
        return null;
    }

    @Override
    public <T> List<T> findAll(Class<T> entityType) throws DbException {
        return null;
    }

    @Override
    public <T> Selector<T> selector(Class<T> entityType) throws DbException {
        return null;
    }

    @Override
    public DbModel findDbModelFirst(SqlInfo sqlInfo) throws DbException {
        return null;
    }

    @Override
    public List<DbModel> findDbModelAll(SqlInfo sqlInfo) throws DbException {
        return null;
    }

    @Override
    public <T> TableEntity<T> getTable(Class<T> entityType) throws DbException {
        return null;
    }

    @Override
    public void dropTable(Class<?> entityType) throws DbException {

    }

    @Override
    public void addColumn(Class<?> entityType, String column) throws DbException {

    }

    @Override
    public void dropDb() throws DbException {

    }

    @Override
    public void close() throws IOException {

    }

    @Override
    public int executeUpdateDelete(SqlInfo sqlInfo) throws DbException {
        return 0;
    }

    @Override
    public int executeUpdateDelete(String sql) throws DbException {
        return 0;
    }

    @Override
    public void execNonQuery(SqlInfo sqlInfo) throws DbException {

    }

    @Override
    public void execNonQuery(String sql) throws DbException {

    }

    @Override
    public Cursor execQuery(SqlInfo sqlInfo) throws DbException {
        return null;
    }

    @Override
    public Cursor execQuery(String sql) throws DbException {
        return null;
    }
}
