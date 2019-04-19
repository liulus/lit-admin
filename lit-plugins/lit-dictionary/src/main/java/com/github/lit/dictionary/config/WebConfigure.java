package com.github.lit.dictionary.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-02-22 13:29
 */
//@Configuration
public class WebConfigure {

    @Bean
    public WebMvcConfigurer dictionaryWebConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/dictionary/list").setViewName("dictionary");
                registry.addViewController("/dictionary/{id}").setViewName("dictionary-detail");
            }
        };
    }

//    public WebSecu


}
