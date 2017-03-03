package com.lit.test.config;

import com.lit.commons.utils.PropertyUtils;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * User : liulu
 * Date : 2017-2-21 17:49
 * version $Id: SpringConfig.java, v 0.1 Exp $
 */
@Configuration
@ComponentScan(basePackages = "com.lit")
public class SpringConfig {

    @Bean
    public DataSource c3p0DataSources () throws PropertyVetoException {
        String configFile = "config";

        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setJdbcUrl(PropertyUtils.getProperty(configFile, "db.url"));
        source.setDriverClass(PropertyUtils.getProperty(configFile, "db.driver"));
        source.setUser(PropertyUtils.getProperty(configFile, "db.user"));
        source.setPassword(PropertyUtils.getProperty(configFile, "db.password"));
        source.setMinPoolSize(Integer.valueOf(PropertyUtils.getProperty(configFile, "pool.minPoolSize")));
        source.setMaxPoolSize(Integer.valueOf(PropertyUtils.getProperty(configFile, "pool.maxPoolSize")));
        source.setAutoCommitOnClose(false);
        source.setCheckoutTimeout(Integer.valueOf(PropertyUtils.getProperty(configFile, "pool.checkoutTimeout")));
        source.setAcquireRetryAttempts(2);

        return source;
    }

    @Bean
    public JdbcOperations jdbcTemplate (DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }

}
