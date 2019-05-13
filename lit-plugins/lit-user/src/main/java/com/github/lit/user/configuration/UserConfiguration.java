package com.github.lit.user.configuration;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-01
 */
@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class UserConfiguration implements WebMvcConfigurer {

    @Resource
    private UserProperties userProperties;

    @Bean
    public WebMvcConfigurer userWebMvcConfiguration() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {

                registry.addViewController("/login").setViewName(userProperties.getLoginPage());
                registry.addViewController("/forget").setViewName(userProperties.getForgetPage());
                if (userProperties.getEnableRegister()) {
                    registry.addViewController("/register").setViewName(userProperties.getRegisterPage());
                }

                registry.addViewController("/user/list").setViewName("user-list");
                registry.addViewController("/user/add").setViewName("user-add");

                registry.addViewController("/organization").setViewName("organization");
                registry.addViewController("/corporation/info").setViewName("corporation-info");
            }
        };
    }


}
