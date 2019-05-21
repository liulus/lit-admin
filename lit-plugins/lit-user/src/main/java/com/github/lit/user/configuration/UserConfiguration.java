package com.github.lit.user.configuration;

import com.github.lit.plugin.core.context.PluginRouteContext;
import com.github.lit.plugin.core.model.Route;
import com.github.lit.plugin.core.util.PluginUtils;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
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

    private static final String USER_INDEX_PATH = "/user/index";
    private static final String USER_ADD_PATH = "/user/add";
    private static final String ORG_INDEX_PATH = "/organization/index";
    private static final String CORP_INDEX_PATH = "/corporation/index";

    private static final String USER_INDEX_VIEW = "/views/user-index.js";
    private static final String USER_ADD_VIEW = "/views/user-edit.js";
    private static final String ORG_INDEX_VIEW = "/views/organization.js";
    private static final String CORP_INDEX_VIEW = "/views/corporation.js";

    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {
        // 用户列表
        PluginRouteContext.addRoute(new Route(USER_INDEX_PATH, USER_INDEX_VIEW));
        // 新增用户
        PluginRouteContext.addRoute(new Route(USER_ADD_PATH, USER_ADD_VIEW));
        // 企业管理
        PluginRouteContext.addRoute(new Route(CORP_INDEX_PATH, CORP_INDEX_VIEW));
        // 部门管理
        PluginRouteContext.addRoute(new Route(ORG_INDEX_PATH, ORG_INDEX_VIEW));
    }

    @Controller
    public static class UserRouteController {

        @GetMapping(USER_INDEX_PATH)
        public String userIndex(ModelMap map) {
            return PluginUtils.addView(map, USER_INDEX_VIEW);
        }

        @GetMapping(USER_ADD_PATH)
        public String addUser(ModelMap map) {
            return PluginUtils.addView(map, USER_ADD_VIEW);
        }

        @GetMapping(ORG_INDEX_PATH)
        public String orgIndex(ModelMap map) {
            return PluginUtils.addView(map, ORG_INDEX_VIEW);
        }

        @GetMapping(CORP_INDEX_PATH)
        public String corpIndex(ModelMap map) {
            return PluginUtils.addView(map, CORP_INDEX_VIEW);
        }

    }

}
