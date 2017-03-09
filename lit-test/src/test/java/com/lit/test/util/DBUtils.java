package com.lit.test.util;

import com.lit.commons.util.PropertyUtils;
import com.lit.dao.JdbcDao;
import com.lit.dao.impl.JdbcDaoImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * User : liulu
 * Date : 2017-1-9 21:14
 * version $Id: DBUtils.java, v 0.1 Exp $
 */
public class DBUtils {

    private static JdbcDao jdbcDao;

    private static JdbcOperations jdbcTemplate;

    private static DataSource dataSource;

    public static JdbcDao getJdbcDao() throws PropertyVetoException {
        if (jdbcDao == null) {
            jdbcDao = new JdbcDaoImpl(getJdbcOperations());
        }
        return jdbcDao;
    }

    public static JdbcOperations getJdbcOperations() throws PropertyVetoException {
        if (jdbcTemplate == null) {
            jdbcTemplate = new JdbcTemplate(getDataSource());
        }
        return jdbcTemplate;
    }

    public static DataSource getDataSource() throws PropertyVetoException {
        if (dataSource == null) {
            String config = "config";
            ComboPooledDataSource source = new ComboPooledDataSource();
            source.setJdbcUrl(PropertyUtils.getProperty(config, "db.url"));
            source.setDriverClass(PropertyUtils.getProperty(config, "db.driver"));
            source.setUser(PropertyUtils.getProperty(config, "db.user"));
            source.setPassword(PropertyUtils.getProperty(config, "db.password"));
            source.setMinPoolSize(Integer.valueOf(PropertyUtils.getProperty(config, "pool.minPoolSize")));
            source.setMaxPoolSize(Integer.valueOf(PropertyUtils.getProperty(config, "pool.maxPoolSize")));
            source.setAutoCommitOnClose(false);
            source.setCheckoutTimeout(Integer.valueOf(PropertyUtils.getProperty(config, "pool.checkoutTimeout")));
            source.setAcquireRetryAttempts(2);
            dataSource = source;
        }
        return dataSource;
    }
}
