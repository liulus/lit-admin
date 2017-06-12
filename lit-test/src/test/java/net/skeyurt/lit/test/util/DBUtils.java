package net.skeyurt.lit.test.util;

import net.skeyurt.lit.commons.util.PropertyUtils;
//import net.skeyurt.lit.jdbc.impl.JdbcDaoImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.skeyurt.lit.jdbc.JdbcTools;
import net.skeyurt.lit.jdbc.spring.JdbcTemplateToolsImpl;
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

    private static JdbcTools jdbcTools;

    private static JdbcOperations jdbcTemplate;

    private static DataSource dataSource;

    public static JdbcTools getJdbcDao() throws PropertyVetoException {
        if (jdbcTools == null) {
            jdbcTools = new JdbcTemplateToolsImpl(getJdbcOperations());
        }
        return jdbcTools;
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
            String db = "mysql.";
            ComboPooledDataSource source = new ComboPooledDataSource();
            source.setJdbcUrl(PropertyUtils.getProperty(config, db + "url"));
            source.setDriverClass(PropertyUtils.getProperty(config, db + "driver"));
            source.setUser(PropertyUtils.getProperty(config, db + "user"));
            source.setPassword(PropertyUtils.getProperty(config, db + "password"));
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
