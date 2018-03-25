package com.github.lit.plugin.context;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.lit.commons.spring.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Collections;
import java.util.List;

/**
 * User : liulu
 * Date : 2017/8/13 7:26
 * version $Id: PluginConfig.java, v 0.1 Exp $
 */
@Configuration
@ConditionalOnClass({ContentNegotiatingViewResolver.class, ObjectMapper.class})
public class PluginConfig {



    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(List<ViewResolver> viewResolvers, ObjectMapper objectMapper) {

        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();

        ContentNegotiationManagerFactoryBean bean = new ContentNegotiationManagerFactoryBean();
        bean.setIgnoreAcceptHeader(true);
        bean.setDefaultContentType(MediaType.TEXT_HTML);

        View jackson2JsonView = new MappingJackson2JsonView(objectMapper);

        viewResolver.setContentNegotiationManager(bean.getObject());
        viewResolver.setDefaultViews(Collections.singletonList(jackson2JsonView));
        viewResolver.setViewResolvers(viewResolvers);

        return viewResolver;
    }
}
