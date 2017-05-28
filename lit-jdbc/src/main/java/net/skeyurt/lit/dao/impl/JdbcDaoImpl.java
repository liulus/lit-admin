package net.skeyurt.lit.dao.impl;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.PageService;
import net.skeyurt.lit.commons.page.Pager;
import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.builder.Criteria;
import net.skeyurt.lit.dao.enums.Operator;
import net.skeyurt.lit.dao.generator.KeyGenerator;
import net.skeyurt.lit.dao.model.SqlResult;
import net.skeyurt.lit.dao.transfer.AnnotationRowMapper;
import net.skeyurt.lit.dao.transfer.CriteriaTransfer;
import net.skeyurt.lit.dao.transfer.CriteriaTransferFactory;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.ArgumentPreparedStatementSetter;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017-1-7 16:29
 * version $Id: JdbcDaoImpl.java, v 0.1 Exp $
 */
@Slf4j
@NoArgsConstructor
public class JdbcDaoImpl implements JdbcDao {

    @Getter
    @Setter
    private JdbcOperations jdbcTemplate;

    @Setter
    private String dbName;

    public JdbcDaoImpl(JdbcOperations jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public <T, ID extends Serializable> ID insert(T t) {
        final Criteria criteria = t instanceof Criteria ? ((Criteria) t)
                : Criteria.insert(t.getClass()).initEntity(t, true);

        final SqlResult sqlResult;

        Serializable idValue = null;
        KeyGenerator keyGenerator = criteria.getKeyGenerator();
        if (keyGenerator != null) {
            idValue = keyGenerator.generateKey(getDbName());
            criteria.into(criteria.getPkName(), idValue, true);
        }

        sqlResult = criteria.build();
        final Object[] args = sqlResult.getParams().toArray();
        if (criteria.isAutoGenerateKey() && (keyGenerator == null || keyGenerator.isGenerateBySql())) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            log.info("\nsql : {}  args : {}", sqlResult.getSql(), Arrays.toString(args));
            jdbcTemplate.update(new PreparedStatementCreator() {
                @Override
                public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                    PreparedStatement ps = connection.prepareStatement(sqlResult.getSql(), new String[]{criteria.getColumnName(criteria.getPkName())});
                    ArgumentPreparedStatementSetter apss = new ArgumentPreparedStatementSetter(args);
                    apss.setValues(ps);
                    return ps;
                }
            }, keyHolder);
            //noinspection unchecked
            return (ID) Long.valueOf(keyHolder.getKey().longValue());
        }

        executeUpdate(sqlResult.getSql(), args);
        //noinspection unchecked
        return (ID) idValue;
    }

    @Override
    public <T> int delete(T t) {
        SqlResult sqlResult = t instanceof Criteria ? ((Criteria) t).build()
                : Criteria.delete(t.getClass()).initEntity(t, true).build();
        return executeUpdate(sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public <T> int deleteByIds(Class<T> clazz, Serializable... ids) {
        Criteria<T> criteria = Criteria.delete(clazz);
        SqlResult sqlResult = criteria.where(criteria.getPkName(), Operator.IN, (Object[]) ids).build();
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
        log.info("\nsql : {}  args : {}", sql, Arrays.toString(args));
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        Criteria<T> criteria = Criteria.select(clazz);
        criteria.where(criteria.getPkName(), id);
        return queryForSingle(criteria);
    }

    @Override
    public <T> T findByProperty(Class<T> clazz, String propertyName, Object propertyValue) {
        return queryForSingle(Criteria.select(clazz).where(propertyName, propertyValue));
    }

    @Override
    public <T, Qo> T queryForSingle(Class<T> clazz, Qo qo) {
        return queryForSingle(getCriteria(clazz, qo, CriteriaTransferFactory.createTransfer(qo)));
    }

    @Override
    public <T> T queryForSingle(Criteria<T> criteria) {
        SqlResult sqlResult = criteria.build();
        return queryForSingle(criteria.getEntityClass(), sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public <T> T queryForSingle(Class<T> clazz, String sql, Object... args) {
        List<T> results = query(clazz, sql, args);
        if (results == null || results.size() == 0) {
            return null;
        }
        if (results.size() > 1) {
            throw new IncorrectResultSizeDataAccessException(1, results.size() );
        }
        return results.iterator().next();
    }

    @Override
    public Map<String, Object> queryForSingle(String sql, Object... args) {
        return jdbcTemplate.queryForMap(sql, args);
    }

    @Override
    public <T, Qo> List<T> query(Class<T> clazz, Qo qo) {
        return query(getCriteria(clazz, qo, CriteriaTransferFactory.createTransfer(qo)));
    }

    @Override
    public <T, Qo> List<T> query(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer) {
        return query(getCriteria(clazz, qo, transfer));
    }

    @Override
    public <T> List<T> query(Criteria<T> criteria) {
        SqlResult sqlResult = criteria.build();
        return query(criteria.getEntityClass(), sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public <T> List<T> query(Class<T> clazz, String sql, Object... args) {
        return jdbcTemplate.query(sql, args, new AnnotationRowMapper<>(clazz));
    }

    @Override
    public <T, Qo extends Pager> PageList<T> queryPageList(Class<T> clazz, Qo qo) {
        return queryPageList(clazz, qo, CriteriaTransferFactory.createTransfer(qo));
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
    public <T, Qo> int count(Class<T> clazz, Qo qo) {
        return count(getCriteria(clazz, qo, CriteriaTransferFactory.createTransfer(qo)));
    }

    @Override
    public <T, Qo> int count(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer) {
        return count(getCriteria(clazz, qo, transfer));
    }

    @Override
    public int count(Criteria criteria) {
        SqlResult sqlResult = criteria.addFunc("count(*)").build();
        return count(sqlResult.getSql(), sqlResult.getParams().toArray());
    }

    @Override
    public int count(String sql, Object... args) {
        return jdbcTemplate.queryForObject(sql, args, int.class);
    }

    @Override
    public <T> T queryForObject(Criteria criteria, Class<T> clazz) {
        SqlResult sqlResult = criteria.build();
        return jdbcTemplate.queryForObject(sqlResult.getSql(), sqlResult.getParams().toArray(), clazz);
    }


    private <T, Qo> Criteria<T> getCriteria(Class<T> clazz, Qo qo, CriteriaTransfer<Qo> transfer) {
        Criteria<T> criteria = Criteria.select(clazz);
        transfer.transQuery(qo, criteria, clazz);
        return criteria;
    }

    private String getDbName() {
        if (StringUtils.isEmpty(dbName)) {
            dbName = jdbcTemplate.execute(new ConnectionCallback<String>() {
                @Override
                public String doInConnection(Connection con) throws SQLException, DataAccessException {
                    return con.getMetaData().getDatabaseProductName().toUpperCase();
                }
            });
        }
        return dbName;
    }

}
