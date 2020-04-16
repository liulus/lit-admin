package com.github.lit.user.configuration;

import com.github.lit.plugin.core.model.Route;
import com.github.lit.plugin.core.util.PluginUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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
            }
        };
    }

    private static final String USER_INDEX = "/user/index";
    private static final String USER_ADD = "/user/add";
    private static final String ORG_INDEX = "/organization/index";
    private static final String CORP_INDEX = "/corporation/index";

    private static final String USER_INDEX_VIEW = "/views/user-index.js";
    private static final String USER_ADD_VIEW = "/views/user-edit.js";
    private static final String ORG_INDEX_VIEW = "/views/organization.js";
    private static final String CORP_INDEX_VIEW = "/views/corporation.js";


    @Bean
    public Route userIndex() {
        return new Route(USER_INDEX, USER_INDEX_VIEW);
    }

    @Bean
    public Route userAdd() {
        return new Route(USER_ADD, USER_ADD_VIEW);
    }

    @Bean
    public Route corpIndex() {
        return new Route(CORP_INDEX, CORP_INDEX_VIEW);
    }

    @Bean
    public Route orgIndex() {
        return new Route(ORG_INDEX, ORG_INDEX_VIEW);
    }


    @Controller
    public static class UserRouteController {

        @GetMapping(USER_INDEX)
        public String userIndex(ModelMap map) {
            return PluginUtils.addView(map, USER_INDEX_VIEW);
        }

        @GetMapping(USER_ADD)
        public String addUser(ModelMap map) {
            return PluginUtils.addView(map, USER_ADD_VIEW);
        }

        @GetMapping(ORG_INDEX)
        public String orgIndex(ModelMap map) {
            return PluginUtils.addView(map, ORG_INDEX_VIEW);
        }

        @GetMapping(CORP_INDEX)
        public String corpIndex(ModelMap map) {
            return PluginUtils.addView(map, CORP_INDEX_VIEW);
        }

    }

}
