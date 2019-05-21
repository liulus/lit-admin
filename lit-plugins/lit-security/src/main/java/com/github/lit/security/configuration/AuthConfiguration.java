package com.github.lit.security.configuration;

import com.github.lit.plugin.core.context.PluginRouteContext;
import com.github.lit.plugin.core.model.Route;
import com.github.lit.plugin.core.util.PluginUtils;
import com.github.lit.user.configuration.UserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author liulu
 * @version v1.0
 * date 2019-05-01
 */
@Configuration
@EnableConfigurationProperties(UserProperties.class)
public class AuthConfiguration implements WebMvcConfigurer {

    private static final String ROLE_INDEX_PATH = "/role/index";
    private static final String USER_ADD_PATH = "/user/add";
    private static final String ORG_INDEX_PATH = "/organization/index";
    private static final String CORP_INDEX_PATH = "/corporation/index";

    private static final String ROLE_INDEX_VIEW = "/views/role.js";
    private static final String USER_ADD_VIEW = "/views/user-edit.js";
    private static final String ORG_INDEX_VIEW = "/views/organization.js";
    private static final String CORP_INDEX_VIEW = "/views/corporation.js";

    @EventListener
    public void appStartListener(ContextRefreshedEvent contextRefreshedEvent) {
        PluginRouteContext.addRoute(new Route(ROLE_INDEX_PATH, ROLE_INDEX_VIEW));
    }

    @Controller
    public static class UserRouteController {

        @GetMapping(ROLE_INDEX_PATH)
        public String roleIndex(ModelMap map) {
            return PluginUtils.addView(map, ROLE_INDEX_VIEW);
        }

//        @GetMapping(USER_ADD_PATH)
//        public String addUser(ModelMap map) {
//            return PluginUtils.addView(map, USER_ADD_VIEW);
//        }
//
//        @GetMapping(ORG_INDEX_PATH)
//        public String orgIndex(ModelMap map) {
//            return PluginUtils.addView(map, ORG_INDEX_VIEW);
//        }
//
//        @GetMapping(CORP_INDEX_PATH)
//        public String corpIndex(ModelMap map) {
//            return PluginUtils.addView(map, CORP_INDEX_VIEW);
//        }

    }

}
