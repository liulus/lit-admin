package com.github.lit.menu.configuration;

import com.github.lit.plugin.core.context.PluginRouteContext;
import com.github.lit.plugin.core.model.Route;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-04-23
 */
@Configuration
public class MenuConfiguration {

    @Bean
    public WebMvcConfigurer menuWebMvcConfigure() {
        return new WebMvcConfigurer() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/menu/index").setViewName("menu");
            }
        };
    }

    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {
        Route route = new Route("menu", "/menu/index", "/js/menu.js");
        PluginRouteContext.addRoute(route);
    }

}


