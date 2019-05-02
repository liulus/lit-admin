package com.github.lit.user.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-01
 */
@Configuration
public class UserConfigure {


    @Bean
    public WebMvcConfigurer userWebMvcConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/user/list").setViewName("user-list");
                registry.addViewController("/user/add").setViewName("user-add");

                registry.addViewController("/organization").setViewName("organization");
                registry.addViewController("/corporation/info").setViewName("corporation-info");
            }
        };
    }




}
