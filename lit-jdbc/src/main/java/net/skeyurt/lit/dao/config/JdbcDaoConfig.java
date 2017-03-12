package net.skeyurt.lit.dao.config;

import net.skeyurt.lit.commons.context.ApplicationContextUtils;
import net.skeyurt.lit.commons.context.GlobalParam;
import net.skeyurt.lit.commons.page.PageInterceptor;
import net.skeyurt.lit.commons.page.PageSqlHandler;
import net.skeyurt.lit.dao.JdbcDao;
import net.skeyurt.lit.dao.impl.JdbcDaoImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/3/12 20:20
 * version $Id: JdbcConfig.java, v 0.1 Exp $
 */
@Configuration
@EnableAspectJAutoProxy
public class JdbcDaoConfig {

    @Bean
    @Autowired(required = false)
    public PageInterceptor pageInterceptor(PageSqlHandler pageSqlHandler) {
        return new PageInterceptor(pageSqlHandler);
    }

    @Bean
    public JdbcDao jdbcDao(ApplicationContext context, Environment environment) {
        String templateBeanName = environment.getProperty("lit.jdbc.template");
        if (StringUtils.isEmpty(templateBeanName)) {
            Map<String, JdbcOperations> jdbcOperationsBeans = context.getBeansOfType(JdbcOperations.class);
            if (jdbcOperationsBeans != null && jdbcOperationsBeans.size() == 1) {
                return new JdbcDaoImpl(jdbcOperationsBeans.values().iterator().next());
            }
        } else {
            Object bean = context.getBean(templateBeanName);
            if (bean != null) {
                return new JdbcDaoImpl((JdbcOperations) bean);
            }
        }

        String dataSourceBeanName = environment.getProperty("lit.jdbc.datasource");
        if (StringUtils.isEmpty(dataSourceBeanName)) {
            Map<String, DataSource> dataSourceBeans = context.getBeansOfType(DataSource.class);
            if (dataSourceBeans != null && dataSourceBeans.size() == 1) {
                return new JdbcDaoImpl(new JdbcTemplate(dataSourceBeans.values().iterator().next()));
            }
        } else {
            Object bean = context.getBean(dataSourceBeanName);
            if (bean != null) {
                return new JdbcDaoImpl(new JdbcTemplate((DataSource) bean));
            }
        }
        return null;
    }

    @PostConstruct
    public void init() throws SQLException {
        JdbcDao jdbcDao = ApplicationContextUtils.getBean(JdbcDao.class);
        JdbcTemplate jdbcTemplate = (JdbcTemplate) jdbcDao.getJdbcTemplate();
        GlobalParam.put("dbName", jdbcTemplate.getDataSource().getConnection().getMetaData().getDatabaseProductName().toUpperCase());
    }
}
