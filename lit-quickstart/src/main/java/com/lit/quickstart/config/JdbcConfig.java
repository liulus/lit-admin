package com.lit.quickstart.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * User : liulu
 * Date : 2016-10-7 15:36
 */
@Configuration
public class JdbcConfig {

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setJdbcUrl(env.getProperty("db.url"));
        dataSource.setDriverClass(env.getProperty("db.driver"));
        dataSource.setUser(env.getProperty("db.user"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setMinPoolSize(Integer.valueOf(env.getProperty("pool.minPoolSize")));
        dataSource.setMaxPoolSize(Integer.valueOf(env.getProperty("pool.maxPoolSize")));
        dataSource.setAutoCommitOnClose(false);
        dataSource.setCheckoutTimeout(Integer.valueOf(env.getProperty("pool.checkoutTimeout")));
        dataSource.setAcquireRetryAttempts(2);
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcTemplate jdbcTemplate (DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
