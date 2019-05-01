package com.github.lit.param.configure;

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
public class SysParamConfigure {

    @Bean
    public WebMvcConfigurer sysParamWebMvcConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/param/list").setViewName("param");
            }
        };
    }

}


