package net.skeyurt.lit.jdbc;

import lombok.Setter;
import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.Pager;
import net.skeyurt.lit.jdbc.enums.Logic;
import net.skeyurt.lit.jdbc.pager.DefaultPageHandler;
import net.skeyurt.lit.jdbc.pager.StatementPageHandler;
import net.skeyurt.lit.jdbc.sta.*;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/6/4 8:45
 * version $Id: AbstractJdbcTools.java, v 0.1 Exp $
 */
public abstract class AbstractJdbcTools implements JdbcTools {

    @Setter
    protected DataSource dataSource;

    @Setter
    private StatementExecutor statementExecutor;

    @Setter
    private StatementPageHandler statementPageHandler;

    @Setter
    private String dbName;


    @Override
    public <T> Object insert(T t) {
        return createInsert(t.getClass()).initEntity(t).execute();
    }

    @Override
    public <T> int delete(T t) {
        return createDelete(t.getClass()).initEntity(t).execute();
    }

    @Override
    public <T> int deleteByIds(Class<T> clazz, Serializable... ids) {
        return createDelete(clazz).idCondition(Logic.IN, (Object[]) ids).execute();
    }

    @Override
    public <T> int update(T t) {
        return createUpdate(t.getClass()).initEntity(t, true).execute();
    }

    @Override
    public <T> int update(T t, boolean isIgnoreNull) {
        return createUpdate(t.getClass()).initEntity(t,isIgnoreNull).execute();
    }

    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        return createSelect(clazz).idCondition(id).single();
    }

    @Override
    public <T> T findByProperty(Class<T> clazz, String propertyName, Object propertyValue) {
        return createSelect(clazz).where(propertyName, propertyValue).single();
    }

    @Override
    public <T, Qo> T queryForSingle(Class<T> clazz, Qo qo) {
        return createSelect(clazz).beanCondition(qo).single();
    }

    @Override
    public <T, Qo> List<T> query(Class<T> clazz, Qo qo) {
        return createSelect(clazz).beanCondition(qo).list();
    }

    @Override
    public <T, Qo extends Pager> PageList<T> queryPageList(Class<T> clazz, Qo qo) {
        return (PageList<T>) createSelect(clazz).beanCondition(qo)
                .page(qo.getPageNum(),qo.getPageSize(),qo.isCount()).list();
    }

    @Override
    public <T, Qo> int count(Class<T> clazz, Qo qo) {
        return createSelect(clazz).beanCondition(qo).count();
    }

    public <T> Select<T> createSelect(Class<T> clazz) {

        return StatementFactory.createSelect(clazz, getStatementExecutor(), getStatementPageHandler(), getDbName());
    }

    public Insert createInsert(Class<?> clazz) {

        return StatementFactory.createInsert(clazz, getStatementExecutor(), getStatementPageHandler(), getDbName());
    }

    public Delete createDelete(Class<?> clazz) {

        return StatementFactory.createDelete(clazz, getStatementExecutor(), getStatementPageHandler(), getDbName());
    }

    public Update createUpdate(Class<?> clazz) {

        return StatementFactory.createUpdate(clazz, getStatementExecutor(), getStatementPageHandler(), getDbName());
    }


    protected abstract StatementExecutor getDefaultExecutor();

    protected abstract String getDefaultDbName();


    private StatementExecutor getStatementExecutor() {
        if (statementExecutor == null) {
            statementExecutor = getDefaultExecutor();
        }
        return statementExecutor;
    }

    private StatementPageHandler getStatementPageHandler() {
        if (statementPageHandler == null) {
            statementPageHandler = new DefaultPageHandler();
        }
        return statementPageHandler;
    }

    private String getDbName() {
        if (dbName == null) {
            dbName = getDefaultDbName();
        }
        return dbName;
    }


}
