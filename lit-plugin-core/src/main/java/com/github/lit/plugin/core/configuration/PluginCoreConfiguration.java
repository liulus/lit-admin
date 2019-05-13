package com.github.lit.plugin.core.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-13
 */
@ComponentScan("com.github.lit")
@Configuration
public class PluginCoreConfiguration {


    @Bean
    public WebMvcConfigurer pluginCoreWebMvcConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/index").setViewName("index");
            }
        };
    }





}
