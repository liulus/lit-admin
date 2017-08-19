package net.skeyurt.lit.plugin.context;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.web.accept.ContentNegotiationManagerFactoryBean;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.velocity.VelocityConfig;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;
import org.springframework.web.servlet.view.velocity.VelocityViewResolver;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * User : liulu
 * Date : 2017/8/13 7:26
 * version $Id: PluginConfig.java, v 0.1 Exp $
 */
@Configuration
public class PluginConfig {


//    @Bean
    public VelocityConfig velocityConfig() {
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        velocityConfigurer.setResourceLoaderPath("classpath:/templates/");
        velocityConfigurer.setPreferFileSystemAccess(true);

        Map<String, Object> velocityPropMap = new HashMap<>();
        velocityPropMap.put("input.encoding", "UTF-8");
        velocityPropMap.put("output.encoding", "UTF-8");
        velocityPropMap.put("velocimacro.library", "/templates/macro/pluginMacro.vm");

        velocityConfigurer.setVelocityPropertiesMap(velocityPropMap);

        velocityConfigurer.setConfigLocation(new ClassPathResource("velocity.properties"));

        return velocityConfigurer;
    }

//    @Bean
    public VelocityViewResolver velocityViewResolver() {
        VelocityViewResolver viewResolver = new VelocityViewResolver();

        viewResolver.setSuffix(".vm");
//        viewResolver.setPrefix("/templates/");
        viewResolver.setContentType("text/html; charset=utf-8");
        viewResolver.setExposeRequestAttributes(true);
        viewResolver.setExposeSessionAttributes(true);
        viewResolver.setRequestContextAttribute("rc");


        return viewResolver;
    }


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
