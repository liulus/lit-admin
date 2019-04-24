package com.github.lit.menu.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-23
 */
@Configuration
public class MenuConfigure {

    @Bean
    public WebMvcConfigurer menuWebMvcConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/menu/list").setViewName("menu");
                registry.addViewController("/menu/{id}").setViewName("menu-detail");
            }
        };
    }

}


