package net.skeyurt.lit.dao.impl;

import net.skeyurt.lit.commons.page.PageList;
import net.skeyurt.lit.commons.page.PageService;
import net.skeyurt.lit.commons.page.Pager;
import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.builder.Criteria;
import net.skeyurt.lit.dao.generator.KeyGenerator;
import net.skeyurt.lit.dao.generator.SequenceGenerator;
import net.skeyurt.lit.dao.model.SqlResult;
import net.skeyurt.lit.dao.transfer.AnnotationRowMapper;
import net.skeyurt.lit.dao.transfer.CriteriaTransfer;
import net.skeyurt.lit.dao.transfer.DefaultCriteriaTransfer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
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

    private static final CriteriaTransfer CRITERIA_TRANSFER = new DefaultCriteriaTransfer();

    private String dbName;

    @Getter
    @Setter
    private JdbcOperations jdbcTemplate;

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
            idValue = !(keyGenerator instanceof SequenceGenerator) ? keyGenerator.generateKey(getDbName())
                    : ((SequenceGenerator) keyGenerator).generateSeqKey(getDbName(), criteria.getSequenceName());
            criteria.into(criteria.getPkName(), idValue);
        }

        sqlResult = criteria.build();
        final Object[] args = sqlResult.getParams().toArray();
        if (criteria.isAutoGenerateKey() && (keyGenerator == null || keyGenerator.isGenerateBySql())) {
            KeyHolder keyHolder = new GeneratedKeyHolder();
            log.info("\n sql : {} \nargs : {}\n", sqlResult.getSql(), Arrays.toString(args));
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
        log.info("\n sql : {} \nargs : {}\n", sql, Arrays.toString(args));
        return jdbcTemplate.update(sql, args);
    }

    @Override
    public <T> T get(Class<T> clazz, Serializable id) {
        Criteria<T> criteria = Criteria.select(clazz);
        criteria.where(criteria.getPkName(), id);
        return queryForSingle(criteria);
    }

    @Override
    public <T, Qo> T queryForSingle(Class<T> clazz, Qo qo) {
        return queryForSingle(getCriteria(clazz, qo, CRITERIA_TRANSFER));
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
    public <T, Qo> List<T> query(Class<T> clazz, Qo qo) {
        return query(getCriteria(clazz, qo, CRITERIA_TRANSFER));
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
        //noinspection unchecked
        return queryPageList(clazz, qo, CRITERIA_TRANSFER);
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
        return count(getCriteria(clazz, qo, CRITERIA_TRANSFER));
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


    private <T, Qo> Criteria<T> getCriteria(Class<T> clazz, Qo qo, CriteriaTransfer transfer) {
        Criteria<T> criteria = Criteria.select(clazz);
        //noinspection unchecked
        transfer.transQuery(qo, criteria, clazz);
        return criteria;
    }

    public String getDbName() {
        if (StringUtils.isEmpty(dbName)) {
            dbName = jdbcTemplate.execute(new ConnectionCallback<String>() {
                @Override
                public String doInConnection(Connection con) throws SQLException, DataAccessException {
                    return con.getMetaData().getDatabaseProductName();
                }
            }).toUpperCase();
        }
        return dbName;
    }

}
