package com.github.lit.starter.config;

import com.github.lit.support.web.EnableLitWeb;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-02-12 11:12
 */
@Configuration
@EnableLitWeb
public class WebMvcConfigure implements WebMvcConfigurer {

//    @Bean
//    public ViewResolver jsonViewResolver() {
//        return (viewName, locale) -> new MappingJackson2JsonView();
//    }


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("index");
    }



}
