package com.lit.dao.impl;

import com.lit.commons.page.PageList;
import com.lit.commons.page.PageService;
import com.lit.commons.page.Pager;
import com.lit.dao.JdbcDao;
import com.lit.dao.builder.Criteria;
import com.lit.dao.builder.SqlResult;
import com.lit.dao.transfer.AnnotationRowMapper;
import com.lit.dao.transfer.CriteriaTransfer;
import com.lit.dao.transfer.DefaultCriteriaTransfer;
import org.springframework.jdbc.core.JdbcOperations;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017-1-7 16:29
 * version $Id: JdbcDaoImpl.java, v 0.1 Exp $
 */
public class JdbcDaoImpl implements JdbcDao {

    private static final CriteriaTransfer DEFAULT_TRANSFER = new DefaultCriteriaTransfer();

    private JdbcOperations jdbcTemplate;

    public JdbcDaoImpl() {
    }

    public JdbcDaoImpl(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public JdbcOperations getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <ID, T> ID insert(T t) {
        SqlResult sqlResult = t instanceof Criteria ? ((Criteria) t).build()
                : Criteria.insert(t.getClass()).initEntity(t, true).build();
        executeUpdate(sqlResult.getSql(), sqlResult.getParams().toArray());
        return null;
    }

    @Override
    public <T> int delete(T t) {
        SqlResult sqlResult = t instanceof Criteria ? ((Criteria) t).build()
                : Criteria.delete(t.getClass()).initEntity(t, true).build();
        return executeUpdate(sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public <T> int deleteByIds(Class<T> clazz, Serializable... ids) {
        Criteria criteria = Criteria.delete(clazz);
        SqlResult sqlResult = criteria.where(criteria.getPkName(), "in", (Object[]) ids).build();
        return executeUpdate(sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public <T> int update(T t) {
        return update(t, true);
    }

    @Override
    public <T> int update(T t, boolean isIgnoreNull) {
        SqlResult sqlResult = t instanceof Criteria ? ((Criteria) t).build()
                : Criteria.update(t.getClass()).initEntity(t, isIgnoreNull).build();

        return executeUpdate(sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    private int executeUpdate(String sql, Object... args) {
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        Criteria criteria = Criteria.select(clazz);
        criteria.where(criteria.getPkName(), id);
        return queryForSingle(criteria);
    }

    @Override
    public <T> T queryForSingle(Class<T> clazz, Object qo) {
        //noinspection unchecked
        return queryForSingle(getCriteria(clazz, qo, DEFAULT_TRANSFER));
    }

    @Override
    public <T> T queryForSingle(Criteria criteria) {
        SqlResult sqlResult = criteria.build();
        //noinspection unchecked
        return (T) queryForSingle(criteria.getEntityClass(), sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public <T> T queryForSingle(Class<T> clazz, String sql, Object... args) {
        List<T> results = query(clazz, sql, args);
        if (results == null || results.size() == 0) {
            return null;
        } else if (results.size() > 1) {
            throw new RuntimeException("no unique result for this query!");
        }
        return results.iterator().next();
    }

    @Override
    public Map<String, Object> queryForSingle(String sql, Object... args) {
        return jdbcTemplate.queryForMap(sql, args);
    }

    @Override
    public <T> List<T> query(Class<T> clazz, Object qo) {
        //noinspection unchecked
        return query(clazz, qo, DEFAULT_TRANSFER);
    }

    @Override
    public <T, Qo> List<T> query(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer) {
        return query(getCriteria(clazz, qo, transfer));
    }

    @Override
    public <T> List<T> query(Criteria criteria) {
        SqlResult sqlResult = criteria.build();
        //noinspection unchecked
        return query((Class<T>) criteria.getEntityClass(), sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public <T> List<T> query(Class<T> clazz, String sql, Object... args) {
        return jdbcTemplate.query(sql, args, new AnnotationRowMapper<>(clazz));
    }

    @Override
    public <T> PageList<T> queryPageList(Class<T> clazz, Pager qo) {
        //noinspection unchecked
        return queryPageList(clazz, qo, DEFAULT_TRANSFER);
    }

    @Override
    public <T, Qo extends Pager> PageList<T> queryPageList(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer) {
        PageService.setPager(qo);
        return (PageList<T>) query(clazz, qo, transfer);
    }

    @Override
    public List<Map<String, Object>> query(String sql, Object... args) {
        return jdbcTemplate.queryForList(sql, args);
    }

    @Override
    public <T> int count(Class<T> clazz, Object qo) {
        return count(clazz, qo, DEFAULT_TRANSFER);
    }

    @Override
    public <T, Qo> int count(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer) {
        return count(getCriteria(clazz, qo, transfer));
    }

    @Override
    public int count(Criteria criteria) {
        SqlResult sqlResult = criteria.build();
        return count(sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public int count(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, args, int.class);
    }


    private <Qo> Criteria getCriteria(Class<?> clazz, Qo qo, CriteriaTransfer<Qo> transfer){
        Criteria criteria = Criteria.select(clazz);
        transfer.transQuery(qo, criteria, clazz);
        return criteria;
    }

}
