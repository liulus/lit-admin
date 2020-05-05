package com.lit.service.security.configuration;

import com.lit.service.core.model.Route;
import com.lit.service.core.util.PluginUtils;
import com.lit.service.user.configuration.UserProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

    @Bean
    public Route authIndex(){
        return new Route(ROLE_INDEX_PATH, ROLE_INDEX_VIEW);
    }

    @Controller
    public static class UserRouteController {

        @GetMapping(ROLE_INDEX_PATH)
        public String roleIndex(ModelMap map) {
            return PluginUtils.addView(map, ROLE_INDEX_VIEW);
        }


    }

}
