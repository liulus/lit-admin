package net.skeyurt.lit.dao.config;

import net.skeyurt.lit.commons.context.GlobalParam;
import net.skeyurt.lit.commons.page.PageInterceptor;
import net.skeyurt.lit.commons.page.PageSqlHandler;
import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.impl.JdbcDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ConnectionCallback;
import org.springframework.jdbc.core.JdbcOperations;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/3/12 20:20
 * version $Id: JdbcConfig.java, v 0.1 Exp $
 */
@Configuration
@EnableAspectJAutoProxy
@ComponentScan("net.skeyurt.lit")
public class JdbcDaoConfig {

    @Autowired(required = false)
    private PageSqlHandler pageSqlHandler;

    @Bean
    public PageInterceptor pageInterceptor() {
        return new PageInterceptor(pageSqlHandler);
    }

    @Bean
    public JdbcDao jdbcDao(ApplicationContext context, Environment environment) {

        String templateBeanName = environment.getProperty("lit.jdbc.template");

        if (StringUtils.isNotEmpty(templateBeanName)) {
            JdbcOperations jdbcOperations = (JdbcOperations) context.getBean(templateBeanName);
            initDbName(jdbcOperations);
            return new JdbcDaoImpl(jdbcOperations);
        }
        Map<String, JdbcOperations> jdbcOperationsBeans = context.getBeansOfType(JdbcOperations.class);
        if (jdbcOperationsBeans != null && jdbcOperationsBeans.size() == 1) {
            JdbcOperations jdbcOperations = jdbcOperationsBeans.values().iterator().next();
            initDbName(jdbcOperations);
            return new JdbcDaoImpl(jdbcOperations);
        }
        return null;
    }

    private void initDbName (JdbcOperations jdbcOperations) {
        GlobalParam.put("dbName", jdbcOperations.execute(new ConnectionCallback<String>() {
            @Override
            public String doInConnection(Connection con) throws SQLException, DataAccessException {
                return con.getMetaData().getDatabaseProductName();
            }
        }));
    }

}
