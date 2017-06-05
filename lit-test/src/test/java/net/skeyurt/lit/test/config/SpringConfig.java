package net.skeyurt.lit.test.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.skeyurt.lit.jdbc.spring.config.EnableLitJdbc;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
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
@EnableLitJdbc
@PropertySource("config.properties")
@SuppressWarnings("Duplicates")
public class SpringConfig {

    private static final String mysql = "mysqlDataSource";

    private static final String oracle = "oracleDataSource";

    @Resource
    private Environment env;

    @Bean
    public DataSource mysqlDataSource() throws PropertyVetoException {

        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setJdbcUrl(env.getProperty("mysql.url"));
        source.setDriverClass(env.getProperty("mysql.driver"));
        source.setUser(env.getProperty("mysql.user"));
        source.setPassword(env.getProperty("mysql.password"));
        source.setMinPoolSize(Integer.valueOf(env.getProperty("pool.minPoolSize")));
        source.setMaxPoolSize(Integer.valueOf(env.getProperty("pool.maxPoolSize")));
        source.setAutoCommitOnClose(false);
        source.setCheckoutTimeout(Integer.valueOf(env.getProperty("pool.checkoutTimeout")));
        source.setAcquireRetryAttempts(2);

        return source;
    }

    @Bean
    public DataSource oracleDataSource() throws PropertyVetoException {

        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setJdbcUrl(env.getProperty("oracle.url"));
        source.setDriverClass(env.getProperty("oracle.driver"));
        source.setUser(env.getProperty("oracle.user"));
        source.setPassword(env.getProperty("oracle.password"));
        source.setMinPoolSize(Integer.valueOf(env.getProperty("pool.minPoolSize")));
        source.setMaxPoolSize(Integer.valueOf(env.getProperty("pool.maxPoolSize")));
        source.setAutoCommitOnClose(false);
        source.setCheckoutTimeout(Integer.valueOf(env.getProperty("pool.checkoutTimeout")));
        source.setAcquireRetryAttempts(2);

        return source;
    }


    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier(oracle) DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public JdbcOperations jdbcOperations(@Qualifier(oracle) DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


}
