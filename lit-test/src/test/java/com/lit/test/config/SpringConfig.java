package com.lit.test.config;

import com.lit.dao.JdbcDao;
import com.lit.dao.impl.JdbcDaoImpl;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * User : liulu
 * Date : 2017-2-21 17:49
 * version $Id: SpringConfig.java, v 0.1 Exp $
 */

@Configuration
@ComponentScan(basePackages = "com.lit")
@PropertySource("config.properties")
@SuppressWarnings("Duplicates")
@EnableAspectJAutoProxy
public class SpringConfig {

    @Resource
    private Environment env;

    @Bean
    public DataSource c3p0DataSources() throws PropertyVetoException {

        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setJdbcUrl(env.getProperty("db.url"));
        source.setDriverClass(env.getProperty("db.driver"));
        source.setUser(env.getProperty("db.user"));
        source.setPassword(env.getProperty("db.password"));
        source.setMinPoolSize(Integer.valueOf(env.getProperty("pool.minPoolSize")));
        source.setMaxPoolSize(Integer.valueOf(env.getProperty("pool.maxPoolSize")));
        source.setAutoCommitOnClose(false);
        source.setCheckoutTimeout(Integer.valueOf(env.getProperty("pool.checkoutTimeout")));
        source.setAcquireRetryAttempts(2);

        return source;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcOperations jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

    @Bean
    public JdbcDao jdbcDao(JdbcOperations jdbcOperations) {
        return new JdbcDaoImpl(jdbcOperations);
    }

}
