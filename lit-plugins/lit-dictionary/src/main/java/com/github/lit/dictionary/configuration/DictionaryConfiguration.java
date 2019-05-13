package com.github.lit.dictionary.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-03-01 15:37
 */
@Configuration
public class DictionaryConfiguration {

    @Bean
    public WebMvcConfigurer dictionaryWebMvcConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/dictionary/list").setViewName("dictionary");
                registry.addViewController("/dictionary/{id}").setViewName("dictionary-detail");
            }
        };
    }






}
