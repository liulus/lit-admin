package net.skeyurt.lit.commons.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.skeyurt.lit.commons.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.util.Arrays;
import java.util.Collections;

/**
 * User : liulu
 * Date : 2017/6/18 16:45
 * version $Id: SpringConfig.java, v 0.1 Exp $
 */
@Configuration("litSpringConfig")
@ConditionalOnClass(ViewResolver.class)
public class SpringConfig {


    @Bean
    public ContentNegotiatingViewResolver contentNegotiatingViewResolver(ViewResolver... viewResolvers) {

        ContentNegotiatingViewResolver viewResolver = new ContentNegotiatingViewResolver();

        ContentNegotiationManagerFactoryBean bean = new ContentNegotiationManagerFactoryBean();
        bean.setIgnoreAcceptHeader(true);
        bean.setDefaultContentType(MediaType.TEXT_HTML);

        MappingJackson2JsonView jackson2JsonView = new MappingJackson2JsonView();
        ObjectMapper objectMapper = jackson2JsonView.getObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        viewResolver.setContentNegotiationManager(bean.getObject());
        viewResolver.setDefaultViews(Collections.<View>singletonList(jackson2JsonView));
        viewResolver.setViewResolvers(Arrays.asList(viewResolvers));

        return viewResolver;
    }
}
