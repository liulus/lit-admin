package net.skeyurt.lit.web.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import net.skeyurt.lit.commons.mvc.ModelResultHandlerInterceptor;
import net.skeyurt.lit.jdbc.spring.config.EnableLitJdbc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Collections;

/**
 * User : liulu
 * Date : 2017/3/19 14:39
 * version $Id: config.java, v 0.1 Exp $
 */
@EnableLitJdbc
@Configuration
public class SpringConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private C3p0ConfigProperty c3p0ConfigProperty;

    @Bean
    public DataSource dataSource() throws PropertyVetoException {

        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setJdbcUrl(c3p0ConfigProperty.getJdbcUrl());
        source.setDriverClass(c3p0ConfigProperty.getDriverClass());
        source.setUser(c3p0ConfigProperty.getUser());
        source.setPassword(c3p0ConfigProperty.getPassword());
        source.setMinPoolSize(c3p0ConfigProperty.getMinPoolSize());
        source.setMaxPoolSize(c3p0ConfigProperty.getMaxPoolSize());
        source.setInitialPoolSize(c3p0ConfigProperty.getInitialPoolSize());
        source.setMaxIdleTime(c3p0ConfigProperty.getMaxIdleTime());
        source.setAutoCommitOnClose(c3p0ConfigProperty.isAutoCommitOnClose());
        source.setCheckoutTimeout(c3p0ConfigProperty.getCheckTimeOut());
        source.setAcquireRetryAttempts(2);
        source.setAutomaticTestTable(c3p0ConfigProperty.getAutomaticTestTable());
        return source;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ModelResultHandlerInterceptor()).addPathPatterns("/**");
    }

    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager, ThymeleafViewResolver thymeleafViewResolver) {

        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();

        manager.resolveFileExtensions(MediaType.APPLICATION_JSON);

        MappingJackson2JsonView jackson2JsonView = new MappingJackson2JsonView();
        ObjectMapper objectMapper = jackson2JsonView.getObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        viewResolver.setContentNegotiationManager(manager);
        viewResolver.setDefaultViews(Collections.<View>singletonList(jackson2JsonView));
        viewResolver.setViewResolvers(Collections.<ViewResolver>singletonList(thymeleafViewResolver));

        return viewResolver;
    }


}
